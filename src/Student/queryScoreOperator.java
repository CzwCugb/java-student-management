package Student;

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

public class queryScoreOperator extends JPanel {

    //学生端：成绩查询页面

    private JTable dataTable = new JTable(){
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private DefaultTableModel dataModel = null;
    private JScrollPane dataContainer = new JScrollPane(dataTable);
    private JButton queryScoreButton = new JButton("查询成绩");
    private MySqlConnector conn = new MySqlConnector();

    public queryScoreOperator() {
        this.setVisible(false);
        this.setBounds(0, 90, 800, 600);
        this.setLayout(null);

        dataTable.setFont(new Font("微软雅黑",Font.PLAIN,15));
        dataTable.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 15));
        dataTable.setRowHeight(20);
        dataContainer.setBounds(0,0,800,300);
        dataContainer.setVisible(true);

        queryScoreButton.setBounds(630,310,100,40);
        queryScoreButton.setFont(new Font("微软雅黑",Font.PLAIN,15));

        this.add(dataContainer);
        this.add(queryScoreButton);

        queryScoreButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                initScoreData();
            }
        });
    }

    private void initScoreData(){
        String[] colNameList = new String[]{"课程编号","课程名称","课程学分","课程分数"};
        dataModel = new DefaultTableModel();
        try {
            ResultSet res = conn.sqlQuery("SELECT Course.cNo,Course.cName,Course.cCredit,SC.scScore \n" +
                    "FROM  SC INNER JOIN Course ON SC.cNo = Course.cNo \n" +
                    "WHERE SC.sNo = " + "'" + LoginIn.username +"'");
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
}
