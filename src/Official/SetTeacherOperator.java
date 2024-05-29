package Official;

import LoginIn.MySqlConnector;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import static javax.swing.JOptionPane.showConfirmDialog;
import static javax.swing.JOptionPane.showMessageDialog;

public class SetTeacherOperator extends JPanel {

    private JTable dataTable = new JTable(){
        @Override
        public boolean isCellEditable(int row, int column) {
            DefaultTableModel myModel = (DefaultTableModel)this.getModel();
            if(column != myModel.findColumn("编号")) return true;
            return false;
        }
    };

    private DefaultTableModel dataModel = null;
    private JScrollPane dataContainer = new JScrollPane(dataTable);

    private JTextField noInput = new JTextField();
    private JTextField nameInput = new JTextField();
    private JTextField collegeInput = new JTextField();
    private JLabel noLabel = new JLabel("编号");
    private JLabel nameLabel = new JLabel("姓名");
    private JLabel collegeLabel = new JLabel("学院");
    private JButton queryButton = new JButton("查询");
    private JButton addButton = new JButton("增加");
    private JButton deleteButton = new JButton("删除");
    private MySqlConnector conn = new MySqlConnector();
    private int teacherSum = 0;


    public SetTeacherOperator(){
        this.setVisible(false);
        this.setBounds(0, 90, 800, 600);
        this.setLayout(null);

        dataTable.setFont(new Font("微软雅黑",Font.PLAIN,15));
        dataTable.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 15));
        dataTable.setRowHeight(20);
        dataContainer.setBounds(0,0,800,300);
        dataContainer.setVisible(true);
        initTeacherData();

        noLabel.setFont(new Font("微软雅黑",Font.PLAIN,15));
        nameLabel.setFont(new Font("微软雅黑",Font.PLAIN,15));
        noInput.setFont(new Font("微软雅黑",Font.PLAIN,15));
        nameInput.setFont(new Font("微软雅黑",Font.PLAIN,15));
        collegeLabel.setFont(new Font("微软雅黑",Font.PLAIN,15));
        collegeInput.setFont(new Font("微软雅黑",Font.PLAIN,15));

        noLabel.setBounds(50,320,50,30);
        noInput.setBounds(90,320,120,30);
        collegeLabel.setBounds(50,360,50,30);
        collegeInput.setBounds(90,360,120,30);
        nameLabel.setBounds(230,320,50,30);
        nameInput.setBounds(270,320,80,30);

        queryButton.setFont(new Font("微软雅黑",Font.PLAIN,15));
        addButton.setFont(new Font("微软雅黑",Font.PLAIN,15));
        deleteButton.setFont(new Font("微软雅黑",Font.PLAIN,15));

        queryButton.setBounds(480,320,80,40);
        addButton.setBounds(580,320,80,40);
        deleteButton.setBounds(680,320,80,40);

        this.add(collegeLabel);
        this.add(collegeInput);
        this.add(noInput);
        this.add(noLabel);
        this.add(nameInput);
        this.add(nameLabel);
        this.add(dataContainer);
        this.add(queryButton);
        this.add(addButton);
        this.add(deleteButton);

        queryButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                initTeacherData();
            }
        });

        addButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addTeacherRow();
                initTeacherData();
            }
        });

        deleteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                deleteSelectedRow();
                initTeacherData();
            }
        });

        //计算长度
        try {
            int len = 0;
            ResultSet res = conn.sqlQuery("SELECT * FROM Teacher");
            while(res.next()){
                len++;
            }
            teacherSum = len;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void initTeacherData(){
        String[] colNameList = new String[]{"编号","姓名","性别","年龄","电话","邮箱","学院","密码"};
        dataModel = new DefaultTableModel();
        String sqlstat =  "SELECT * FROM Teacher ";
        String where_ = "";
        if(!noInput.getText().isEmpty()){
            if(where_.isEmpty()) where_ = "WHERE ";
            else where_ = where_ + " AND ";
            where_ = where_ + "tNo = " + "'" + noInput.getText() + "'";
        }
        if(!nameInput.getText().isEmpty()){
            if(where_.isEmpty()) where_ = "WHERE ";
            else where_ = where_ + " AND ";
            where_ = where_ + "tName = " + "'" + nameInput.getText() + "'";
        }
        if(!collegeInput.getText().isEmpty()){
            if(where_.isEmpty()) where_ = "WHERE ";
            else where_ = where_ + " AND ";
            where_ = where_ + "tCollege = " + "'" + collegeInput.getText() + "'";
        }
        sqlstat = sqlstat + where_;
        try {
            ResultSet res = conn.sqlQuery(sqlstat);
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
        dataModel.addTableModelListener(new TableModelListener(){
            @Override
            public void tableChanged(TableModelEvent e) {
                if(e.getType() == TableModelEvent.UPDATE){
                    String[] sqlColName = new String[]{"tNo","tName","tSex","tAge","tTelephone","tEmail","tCollege","tPassword"};
                    int pickedRow = dataTable.getSelectedRow();
                    pickedRow = dataTable.convertRowIndexToModel(pickedRow);
                    int pickColumn = e.getColumn();
                    String tNo_ = dataModel.getValueAt(pickedRow,dataModel.findColumn("编号")).toString();
                    String where_ = " WHERE tNo = " + "'" + tNo_ + "'";
                    try {
                        conn.sqlUpdate("Teacher",sqlColName[pickColumn],dataModel.getValueAt(pickedRow,pickColumn).toString(),where_);
                    } catch (SQLException ex) {
                        showMessageDialog(null,"数据非法","错误",JOptionPane.ERROR_MESSAGE);
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        dataTable.setRowSorter(new TableRowSorter(dataModel));
    }

    private void addTeacherRow(){
        String table_ = "Teacher(tNo,tName)";
        teacherSum++;
        String[] values_ = {"T2024" + String.format("%04d", teacherSum),"undifined"};
        try {
            conn.sqlInsert(table_,values_);
        } catch (SQLException e) {
            showMessageDialog(null,"主键冲突:编号重复","错误",JOptionPane.ERROR_MESSAGE);
            teacherSum--;
            throw new RuntimeException(e);
        }
    }

    private void deleteSelectedRow(){
        int pickedRow = dataTable.getSelectedRow();
        pickedRow = dataTable.convertRowIndexToModel(pickedRow);
        int pkCol = dataModel.findColumn("编号");
        String tNo_ = dataModel.getValueAt(pickedRow,pkCol).toString();
        int comfirm = showConfirmDialog(null,"是否要完全删除该教师及其约束？","提示",JOptionPane.YES_NO_OPTION);
        if(comfirm == JOptionPane.NO_OPTION) return;
        try {
            conn.sqlDelete("Course","WHERE Course.tNo = " + "'" + tNo_ + "'");
            conn.sqlDelete("Teacher","WHERE Teacher.tNo = " + "'" + tNo_ + "'");
            showMessageDialog(null,"删除成功","提示",JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            showMessageDialog(null,"删除失败","错误",JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(e);
        }
    }

}
