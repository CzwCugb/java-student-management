package Student;

import LoginIn.MySqlConnector;
import LoginIn.LoginIn;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

import static javax.swing.JOptionPane.showMessageDialog;

public class PickCourseOperator extends JPanel{

    private JTable dataTable = new JTable(){
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private DefaultTableModel dataModel = null;
    private JScrollPane dataContainer = new JScrollPane(dataTable);
    private JButton queryCourseButton = new JButton("全部课程");
    private JButton queryMyCourseButton = new JButton("我的课程");
    private JButton selectButton = new JButton("选课");
    private JButton deleteButton = new JButton("退课");
    private JLabel yearLabel = new JLabel("学年");
    private JLabel termLabel = new JLabel("学期");
    private JComboBox<String> yearComboBox = new JComboBox<String>();
    private JComboBox<String> termComboBox = new JComboBox<String>();
    private MySqlConnector conn = new MySqlConnector();

    public PickCourseOperator() {

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
        yearComboBox.setBounds(100,310,120,30);
        termComboBox.setBounds(100,360,120,30);

        yearLabel.setFont(new Font("微软雅黑",Font.PLAIN,15));
        yearLabel.setBounds(50,310,50,30);
        termLabel.setFont(new Font("微软雅黑",Font.PLAIN,15));
        termLabel.setBounds(50,360,50,30);

        queryCourseButton.setBounds(310,310,100,40);
        queryMyCourseButton.setBounds(440,310,100,40);
        selectButton.setBounds(570,310,80,40);
        deleteButton.setBounds(680,310,80,40);
        selectButton.setEnabled(false);
        deleteButton.setEnabled(false);

        queryCourseButton.setFont(new Font("微软雅黑",Font.PLAIN,15));
        queryMyCourseButton.setFont(new Font("微软雅黑",Font.PLAIN,15));
        selectButton.setFont(new Font("微软雅黑",Font.PLAIN,15));
        deleteButton.setFont(new Font("微软雅黑",Font.PLAIN,15));


        //查询全部课程
        queryCourseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectButton.setEnabled(true);
                deleteButton.setEnabled(false);
                initAllCourseData();
            }
        });

        //查询我的课程
        queryMyCourseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectButton.setEnabled(false);
                deleteButton.setEnabled(true);
                initMyCourseData();
            }
        });


        //选课按钮
        selectButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!selectButton.isEnabled()) return;
                int pickedRow = dataTable.getSelectedRow();
                pickedRow = dataTable.convertRowIndexToModel(pickedRow);
                if(pickedRow != -1){
                    try {
                        String[] insertValues = new String[3];
                        insertValues[0] = LoginIn.username;
                        insertValues[1] = dataModel.getValueAt(pickedRow,dataModel.findColumn("课程编号")).toString();
                        insertValues[2] = "null";
                        conn.sqlInsert("SC", insertValues);

                        int currentSum = getCurrentSum(dataModel.getValueAt(pickedRow,dataModel.findColumn("课程编号")).toString());
                        String value_ = String.valueOf(currentSum+1);
                        String where_ = "WHERE " + " Course.cNo = " + "'" + insertValues[1] + "'";
                        conn.sqlUpdate("Course","cCurrentSum",value_,where_);

                        showMessageDialog(null,"成功选择课程:" + dataTable.getValueAt(pickedRow,dataModel.findColumn("课程名称")).toString(),
                                "提示",JOptionPane.INFORMATION_MESSAGE);
                    } catch (SQLException ee) {
                        showMessageDialog(null,"课程容量已满 / 你已选中该课程","提示",JOptionPane.ERROR_MESSAGE);
                        throw new RuntimeException(ee);
                    }
                }
                else{
                    showMessageDialog(null,"请先选择课程","提示",JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        //退课按钮
        deleteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!deleteButton.isEnabled()) return;
                int pickedRow = dataTable.getSelectedRow();
                if(pickedRow != -1){
                    try {
                        String cNo_ = dataModel.getValueAt(pickedRow,dataModel.findColumn("课程编号")).toString();
                        String sNo_ = LoginIn.username;

                        int currentSum = getCurrentSum(dataModel.getValueAt(pickedRow,dataModel.findColumn("课程编号")).toString());
                        String value_ = String.valueOf(currentSum-1);
                        String where_1 = "WHERE " + " Course.cNo = " + "'" + cNo_ + "'";
                        conn.sqlUpdate("Course","cCurrentSum",value_,where_1);

                        String where_2 = "WHERE " + "sNo = '" + sNo_ + "' AND " + "cNo = '" + cNo_ + "'";
                        conn.sqlDelete("SC",where_2);

                        showMessageDialog(null,"成功退选课程:" + dataModel.getValueAt(pickedRow,dataModel.findColumn("课程名称")).toString(),
                                "提示",JOptionPane.INFORMATION_MESSAGE);
                        initMyCourseData();

                    } catch (SQLException ex) {
                        showMessageDialog(null,"退课失败","提示",JOptionPane.ERROR_MESSAGE);
                        throw new RuntimeException(ex);
                    }
                }else{
                    showMessageDialog(null,"请先选择课程","提示",JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        this.add(yearComboBox);
        this.add(yearLabel);
        this.add(termComboBox);
        this.add(termLabel);
        this.add(dataContainer);
        this.add(selectButton);
        this.add(deleteButton);
        this.add(queryMyCourseButton);
        this.add(queryCourseButton);
    }

    private void initAllCourseData() {
        String[] colNameList = new String[]{"课程编号","学院","课程名称","任课教师","学年","学期","学分","课容量","当前人数"};
        dataModel = new DefaultTableModel();
        try {
            String where_ = "";
            if(yearComboBox.getSelectedItem() != "全部时间"){
                if(where_.isEmpty()) where_ = "WHERE ";
                else where_ = where_ + "AND ";
                where_ = where_ + "Course.cYear = " + "'" + yearComboBox.getSelectedItem() + "'";
            }
            if(termComboBox.getSelectedItem() != "全年"){
                if(where_.isEmpty()) where_ = "WHERE ";
                else where_ = where_ + "AND ";
                where_ = where_ + "Course.cTerm = " +  "'" + termComboBox.getSelectedItem() +  "'" ;
            }
            ResultSet res = conn.sqlQuery("SELECT Course.cNo,Course.cCollege,Course.cName,Teacher.tName,Course.cYear,Course.cTerm,Course.cCredit,Course.cAmount,Course.cCurrentSum \n" +
                    "FROM Course INNER JOIN Teacher ON Teacher.tNo = Course.tNo " + where_);
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

    private void initMyCourseData(){
        String[] colNameList = new String[]{"课程编号","学院","课程名称","任课教师","学年","学期","学分"};
        dataModel = new DefaultTableModel();
        try {
            String where_ = "WHERE SC.sNo = " + "'" + LoginIn.username +"'";
            if(yearComboBox.getSelectedItem() != "全部时间"){
                where_ = where_ + "AND ";
                where_ = where_ + "Course.cYear = " + "'" + yearComboBox.getSelectedItem() + "'";
            }
            if(termComboBox.getSelectedItem() != "全年"){
                where_ = where_ + "AND ";
                where_ = where_ + "Course.cTerm = " +  "'" + termComboBox.getSelectedItem() +  "'" ;
            }
            ResultSet res = conn.sqlQuery("SELECT Course.cNo,Course.cCollege,Course.cName,Teacher.tName,Course.cYear,Course.cTerm,Course.cCredit\n" +
                    "FROM SC INNER JOIN Course ON SC.cNo = Course.cNo INNER JOIN Teacher ON Course.tNo = Teacher.tNo\n" +
                    where_);
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

    private int getCurrentSum(String pickedcNo_){
        String sql = "SELECT * FROM COURSE WHERE cNo = " + "'"+ pickedcNo_ + "'";
        int sum = 0;
        try{
            ResultSet res = conn.sqlQuery(sql);
            res.next();
            sum = res.getInt("cCurrentSum");
        }catch (SQLException ex){
            throw new RuntimeException(ex);
        }
        return sum;
    }
}
