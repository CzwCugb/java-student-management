package Teacher;

import LoginIn.MySqlConnector;
import LoginIn.LoginIn;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class QueryTasksOperator extends JPanel {

    //教师端：教学任务页面

    private  JTable dataTable = new JTable(){
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private DefaultTableModel dataModel = null;
    private JScrollPane dataContainer = new JScrollPane(dataTable);
    private JButton queryCourseButton = new JButton("查询");
    private JLabel yearLabel = new JLabel("学年");
    private JLabel termLabel = new JLabel("学期");
    private JComboBox<String> yearComboBox = new JComboBox<String>();
    private JComboBox<String> termComboBox = new JComboBox<String>();
    private MySqlConnector conn = new MySqlConnector();

    public QueryTasksOperator(){
        this.setVisible(false);
        this.setBounds(0, 90, 800, 600);
        this.setLayout(null);

        dataTable.setFont(new Font("微软雅黑",Font.PLAIN,15));
        dataTable.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 15));
        dataTable.setRowHeight(20);
        dataContainer.setBounds(0,0,800,300);
        dataContainer.setVisible(true);

        yearComboBox.addItem("全部时间");
        for(int i = 2024  ; i >= 1978; i --) yearComboBox.addItem(String.valueOf(i));
        termComboBox.addItem("全年");termComboBox.addItem("春");termComboBox.addItem("夏");termComboBox.addItem("秋");termComboBox.addItem("冬");
        yearComboBox.setBounds(150,310,100,30);
        termComboBox.setBounds(350,310,100,30);

        yearLabel.setFont(new Font("微软雅黑",Font.PLAIN,15));
        termLabel.setFont(new Font("微软雅黑",Font.PLAIN,15));
        yearLabel.setBounds(100,310,50,30);
        termLabel.setBounds(300,310,50,30);

        queryCourseButton.setBounds(600,310,100,40);
        queryCourseButton.setFont(new Font("微软雅黑",Font.PLAIN,15));
        queryCourseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                initCourseData();
            }
        });

        this.add(yearLabel);
        this.add(termLabel);
        this.add(yearComboBox);
        this.add(termComboBox);
        this.add(dataContainer);
        this.add(queryCourseButton);
    }

    private void initCourseData(){
        String[] colNameList = new String[]{"课程编号","课程名称","学年","学期","课容量","当前人数","所属学院"};
        dataModel = new DefaultTableModel();
        try {
            String where_ = "WHERE tNo = " + "'" + LoginIn.username + "'";
            if(yearComboBox.getSelectedItem() != "全部时间"){
                where_ = where_ + "AND cYear = " + "'" + yearComboBox.getSelectedItem() +  "'" ;
            }
            if(termComboBox.getSelectedItem() != "全年"){
                where_ = where_ + "AND cTerm = " +  "'" + termComboBox.getSelectedItem() +  "'" ;
            }
            ResultSet res = conn.sqlQuery("SELECT Course.cNo,Course.cName,Course.cYear,Course.cTerm,Course.cAmount,Course.cCurrentSum,Course.cCollege" +
                    " FROM Course " + where_);
            ResultSetMetaData md = res.getMetaData();
            int columns = md.getColumnCount();
            for (int i = 1; i <= columns; i++) {
                dataModel.addColumn(colNameList[i-1]);
            }
            while (res.next()) {
                Object[] row = new Object[columns];
                for (int i = 1; i <= columns; i++) {
                    row[i - 1] = res.getObject(i);
                }
                dataModel.addRow(row);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        dataTable.setModel(dataModel);
        dataTable.setRowSorter(new TableRowSorter(dataModel));
    }

    public String getSelectedCourseNo(){
        int row = dataTable.getSelectedRow();
        if(row == -1) return "NOTHING";
        else{
            row = dataTable.convertRowIndexToModel(row);
            return dataModel.getValueAt(row,dataModel.findColumn("课程编号")).toString();
        }


    }
}