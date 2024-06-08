package Teacher;
import LoginIn.MySqlConnector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import static javax.swing.JOptionPane.showMessageDialog;


public class SetScoreOperator extends JPanel {

    //教师端：成绩录入页面

    private String selectedCourseNo = "NOTHING";
    private DefaultTableModel dataModel = null;
    private JTable dataTable = new JTable(){
        @Override
        public boolean isCellEditable(int row, int column) {
            DefaultTableModel tempModel = (DefaultTableModel) this.getModel();
            if(column == tempModel.findColumn("分数")) return true;
            return false;
        }
    };
    private JScrollPane dataContainer = new JScrollPane(dataTable);
    private MySqlConnector conn = new MySqlConnector();
    private JButton clearScoreButton = new JButton("清零成绩");
    private JButton updateScoreButton = new JButton("保存成绩");

    public SetScoreOperator(){
        this.setVisible(false);
        this.setBounds(0, 90, 800, 600);
        this.setLayout(null);

        dataTable.setFont(new Font("微软雅黑",Font.PLAIN,15));
        dataTable.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 15));
        dataTable.setRowHeight(20);
        dataContainer.setBounds(0,0,800,300);
        dataContainer.setVisible(true);

        clearScoreButton.setFont(new Font("微软雅黑",Font.PLAIN,15));
        updateScoreButton.setFont(new Font("微软雅黑",Font.PLAIN,15));
        clearScoreButton.setBounds(480,310,100,40);
        updateScoreButton.setBounds(600,310,100,40);

        updateScoreButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(dataTable.isEditing()){
                    showMessageDialog(null,"请先完成操作","错误",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int rows = dataModel.getRowCount();
                int noCol = dataModel.findColumn("学号");
                int scoreCol = dataModel.findColumn("分数");
                for(int i = 0 ; i < rows ; i ++){
                    String sScore_;
                    if(dataModel.getValueAt(i,scoreCol) == null){
                        sScore_ = "NULL";
                    }else{
                         sScore_ = dataModel.getValueAt(i,scoreCol).toString();
                    }
                    String sNo_ =  dataModel.getValueAt(i,noCol).toString();
                    String where_ = "WHERE SC.sNo = " + "'" + sNo_ + "'" + " AND SC.cNo = " + "'" + selectedCourseNo + "'";
                    try {
                        conn.sqlUpdate("SC","SC.scScore",sScore_,where_);
                    } catch (SQLException ex) {
                        showMessageDialog(null,"数据非法","错误",JOptionPane.ERROR_MESSAGE);
                        throw new RuntimeException(ex);
                    }
                }
                showMessageDialog(null,"修改成功","提示",JOptionPane.INFORMATION_MESSAGE);
                initStudentData();
            }
        });

        clearScoreButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int rows = dataModel.getRowCount();
                int noCol = dataModel.findColumn("课程编号");
                for(int i = 0 ; i < rows ; i ++){
                    String cNo_ =  dataTable.getValueAt(i,noCol).toString();
                    String sScore_ = "NULL";
                    String where_ = "WHERE SC.cNo = " + "'" + cNo_ + "'";
                    try {
                        conn.sqlUpdate("SC","SC.scScore",sScore_,where_);
                    } catch (SQLException ex) {
                        showMessageDialog(null,"数据非法","错误",JOptionPane.ERROR_MESSAGE);
                        throw new RuntimeException(ex);
                    }
                }
                showMessageDialog(null,"学生成绩已清零","提示",JOptionPane.INFORMATION_MESSAGE);
                initStudentData();
            }
        });

        this.add(clearScoreButton);
        this.add(updateScoreButton);
        this.add(dataContainer);
    }

    public void initStudentData(){
        String[] colNameList = new String[]{"课程编号","课程名称","学院","学号","姓名","分数"};
        dataModel = new DefaultTableModel();
        try {
            ResultSet res = conn.sqlQuery("SELECT Course.cNo,Course.cName,Student.sCollege,Student.sNo,Student.sName,SC.scScore FROM\n" +
                    "SC INNER JOIN Course ON SC.cNo = Course.cNo INNER JOIN Student ON SC.sNo = Student.sNo\n" +
                    "WHERE Course.cNo = " + "'" + this.selectedCourseNo + "'");
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

    public void setSelectedCourseNo(String No_){
        this.selectedCourseNo = No_;
    }

}
