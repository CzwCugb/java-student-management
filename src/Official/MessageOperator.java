package Official;

import LoginIn.LoginIn;
import LoginIn.MySqlConnector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import static javax.swing.JOptionPane.*;

public class MessageOperator extends JPanel {

    private JTextField noInput = new JTextField(10);
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

        createLabelAndInput("编号", noInput, 50, 50);
        createLabelAndInput("姓名", nameInput, 50, 100);
        createLabelAndInput("年龄", ageInput, 300, 50);
        createLabelAndInput("性别", sexInput, 550, 50);
        createLabelAndInput("电话", telephoneInput, 300, 100);
        createLabelAndInput("邮箱", emailInput, 550, 100);
        createLabelAndInput("账户密码", pswInput, 50, 150);

        queryButton.setBounds(550,300,80,40);
        queryButton.setFont(new Font("微软雅黑",Font.PLAIN,15));
        updateButton.setBounds(650,300,80,40);
        updateButton.setFont(new Font("微软雅黑",Font.PLAIN,15));

        queryButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    ResultSet res = conn.sqlQuery("SELECT * FROM Official WHERE oNo = '" + LoginIn.username + "'");
                    res.next();
                    noInput.setText(res.getString("oNo"));
                    nameInput.setText(res.getString("oName"));
                    ageInput.setText(res.getString("oAge"));
                    sexInput.setText(res.getString("oSex"));
                    telephoneInput.setText(res.getString("oTelephone"));
                    emailInput.setText(res.getString("oEmail"));
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        updateButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    conn.sqlUpdate("Official","oNo",noInput.getText(),"WHERE oNo = " + "'" + LoginIn.username + "'");
                    conn.sqlUpdate("Official","oAge",ageInput.getText(),"WHERE oNo = " + "'" + LoginIn.username + "'");
                    conn.sqlUpdate("Official","oSex",sexInput.getText(),"WHERE oNo = " + "'" + LoginIn.username + "'");
                    conn.sqlUpdate("Official","oName",nameInput.getText(),"WHERE oNo = " + "'" + LoginIn.username + "'");
                    conn.sqlUpdate("Official","oTelephone",telephoneInput.getText(),"WHERE oNo = " + "'" + LoginIn.username + "'");
                    conn.sqlUpdate("Official","oEmail",emailInput.getText(),"WHERE oNo = " + "'" + LoginIn.username + "'");
                    conn.sqlUpdate("Official","oPassword",pswInput.getText(),"WHERE oNo = " + "'" + LoginIn.username + "'");
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
