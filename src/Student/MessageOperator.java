package Student;

import LoginIn.LoginIn;
import LoginIn.MySqlConnector;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import static javax.swing.JOptionPane.*;

public class MessageOperator extends JPanel{

    //学生端：个人信息页面

    private JTextField noInput = new JTextField(10);
    private JTextField majorInput = new JTextField(10);
    private JTextField colleageInput = new JTextField(10);
    private JTextField nameInput = new JTextField(10);
    private JTextField ageInput = new JTextField(10);
    private JTextField sexInput = new JTextField(10);
    private JTextField yearInput = new JTextField(10);
    private JTextField telephoneInput = new JTextField(10);
    private JTextField emailInput = new JTextField(10);
    private JPasswordField pswInput = new JPasswordField(10);
    public MySqlConnector conn = new MySqlConnector();

    private JButton queryButton = new JButton("查询");
    private JButton updateButton = new JButton("修改");

    public MessageOperator() {

        this.setVisible(false);
        this.setLayout(null);
        this.setBounds(0,90,800,600);

        createLabelAndInput("学号", noInput, 50, 50);
        createLabelAndInput("专业", majorInput, 300, 50);
        createLabelAndInput("学院", colleageInput, 550, 50);
        createLabelAndInput("姓名", nameInput, 50, 100);
        createLabelAndInput("年龄", ageInput, 300, 100);
        createLabelAndInput("性别", sexInput, 550, 100);
        createLabelAndInput("入学时间", yearInput, 50, 150);
        createLabelAndInput("电话", telephoneInput, 300, 150);
        createLabelAndInput("邮箱", emailInput, 550, 150);
        createLabelAndInput("账户密码", pswInput, 50, 200);

        noInput.setEnabled(false);
        noInput.setDisabledTextColor(Color.black);
        nameInput.setEnabled(false);
        nameInput.setDisabledTextColor(Color.black);
        colleageInput.setEnabled(false);
        colleageInput.setDisabledTextColor(Color.black);
        majorInput.setEnabled(false);
        majorInput.setDisabledTextColor(Color.black);

        queryButton.setBounds(550,300,80,40);
        queryButton.setFont(new Font("微软雅黑",Font.PLAIN,15));
        updateButton.setBounds(650,300,80,40);
        updateButton.setFont(new Font("微软雅黑",Font.PLAIN,15));

        queryButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    ResultSet res = conn.sqlQuery("SELECT * FROM STUDENT WHERE sNo = '" + LoginIn.username + "'");
                    res.next();

                    noInput.setText(res.getString("sNo"));
                    nameInput.setText(res.getString("sName"));
                    ageInput.setText(res.getString("sAge"));
                    sexInput.setText(res.getString("sSex"));
                    majorInput.setText(res.getString("sMajor"));
                    colleageInput.setText(res.getString("sCollege"));
                    yearInput.setText(res.getString("sYear"));
                    telephoneInput.setText(res.getString("sTelephone"));
                    emailInput.setText(res.getString("sEmail"));

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        updateButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    conn.sqlUpdate("Student","sNo",noInput.getText(),"WHERE sNo = " + "'" + LoginIn.username + "'");
                    conn.sqlUpdate("Student","sAge",ageInput.getText(),"WHERE sNo = " + "'" + LoginIn.username + "'");
                    conn.sqlUpdate("Student","sSex",sexInput.getText(),"WHERE sNo = " + "'" + LoginIn.username + "'");
                    conn.sqlUpdate("Student","sMajor",majorInput.getText(),"WHERE sNo = " + "'" + LoginIn.username + "'");
                    conn.sqlUpdate("Student","sName",nameInput.getText(),"WHERE sNo = " + "'" + LoginIn.username + "'");
                    conn.sqlUpdate("Student","sCollege",colleageInput.getText(),"WHERE sNo = " + "'" + LoginIn.username + "'");
                    conn.sqlUpdate("Student","sYear",yearInput.getText(),"WHERE sNo = " + "'" + LoginIn.username + "'");
                    conn.sqlUpdate("Student","sTelephone",telephoneInput.getText(),"WHERE sNo = " + "'" + LoginIn.username + "'");
                    conn.sqlUpdate("Student","sEmail",emailInput.getText(),"WHERE sNo = " + "'" + LoginIn.username + "'");
                    conn.sqlUpdate("Student","sPassword",pswInput.getText(),"WHERE sNo = " + "'" + LoginIn.username + "'");

                    showMessageDialog(null,"修改成功","提示",INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    showMessageDialog(null,"数据非法","提示",ERROR_MESSAGE);
                    throw new RuntimeException(ex);
                }
            }
        });

        this.add(queryButton);
        this.add(updateButton);
    }

    private void createLabelAndInput(String labelText, JTextField inputField, int x, int y) {
        JLabel label = new JLabel(labelText);
        label.setBounds(x, y, 80, 30);
        label.setFont(new Font("微软雅黑",Font.BOLD,15));
        this.add(label);
        inputField.setBounds(x + 70, y, 150, 30);
        inputField.setFont(new Font("微软雅黑",Font.PLAIN,15));
        this.add(inputField);
    }


}
