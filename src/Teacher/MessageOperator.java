package Teacher;
import LoginIn.MySqlConnector;
import LoginIn.LoginIn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import static javax.swing.JOptionPane.*;

public class MessageOperator extends JPanel{

    //教师端：个人信息页面

    private JTextField noInput = new JTextField(10);
    private JTextField colleageInput = new JTextField(10);
    private JTextField nameInput = new JTextField(10);
    private JTextField ageInput = new JTextField(10);
    private JTextField sexInput = new JTextField(10);
    private JTextField telephoneInput = new JTextField(10);
    private JTextField emailInput = new JTextField(10);
    private JPasswordField pswInput = new JPasswordField(10);
    public MySqlConnector conn = new MySqlConnector();

    private JButton queryButton = new JButton("查询");
    private JButton updateButton = new JButton("修改");

    public MessageOperator(){
        this.setVisible(false);
        this.setLayout(null);
        this.setBounds(0,90,800,600);

        createLabelAndInput("教学编号", noInput, 50, 50);
        createLabelAndInput("学院", colleageInput, 50, 100);
        createLabelAndInput("姓名", nameInput, 300, 50);
        createLabelAndInput("年龄", ageInput, 550 ,50);
        createLabelAndInput("性别", sexInput, 300, 100);
        createLabelAndInput("电话", telephoneInput, 50, 150);
        createLabelAndInput("邮箱", emailInput, 550, 100);
        createLabelAndInput("账户密码", pswInput, 300, 150);

        noInput.setEnabled(false);
        noInput.setDisabledTextColor(Color.black);
        nameInput.setEnabled(false);
        nameInput.setDisabledTextColor(Color.black);
        colleageInput.setEnabled(false);
        colleageInput.setDisabledTextColor(Color.black);

        queryButton.setBounds(550,300,80,40);
        queryButton.setFont(new Font("微软雅黑",Font.PLAIN,15));
        updateButton.setBounds(650,300,80,40);
        updateButton.setFont(new Font("微软雅黑",Font.PLAIN,15));

        queryButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    ResultSet res = conn.sqlQuery("SELECT * FROM Teacher WHERE tNo = '" + LoginIn.username + "'");
                    res.next();

                    noInput.setText(res.getString("tNo"));
                    nameInput.setText(res.getString("tName"));
                    ageInput.setText(res.getString("tAge"));
                    sexInput.setText(res.getString("tSex"));
                    colleageInput.setText(res.getString("tCollege"));
                    telephoneInput.setText(res.getString("tTelephone"));
                    emailInput.setText(res.getString("tEmail"));

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        updateButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    conn.sqlUpdate("Teacher","tNo",noInput.getText(),"WHERE tNo = " + "'" + LoginIn.username + "'");
                    conn.sqlUpdate("Teacher","tAge",ageInput.getText(),"WHERE tNo = " + "'" + LoginIn.username + "'");
                    conn.sqlUpdate("Teacher","tSex",sexInput.getText(),"WHERE tNo = " + "'" + LoginIn.username + "'");
                    conn.sqlUpdate("Teacher","tName",nameInput.getText(),"WHERE tNo = " + "'" + LoginIn.username + "'");
                    conn.sqlUpdate("Teacher","tCollege",colleageInput.getText(),"WHERE tNo = " + "'" + LoginIn.username + "'");
                    conn.sqlUpdate("Teacher","tTelephone",telephoneInput.getText(),"WHERE tNo = " + "'" + LoginIn.username + "'");
                    conn.sqlUpdate("Teacher","tEmail",emailInput.getText(),"WHERE tNo = " + "'" + LoginIn.username + "'");
                    conn.sqlUpdate("Teacher","tPassword",pswInput.getText(),"WHERE tNo = " + "'" + LoginIn.username + "'");

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