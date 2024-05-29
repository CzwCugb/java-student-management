package LoginIn;

import Official.OfficialManager;
import Student.StudentManager;
import Teacher.MessageOperator;
import Teacher.TeacherManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

public class LoginIn extends JFrame {

    public static void main(String[] args) {
        new LoginIn();
    }

    private Container pane = null;
    private JPanel titleArea = new JPanel();
    private JPanel clickArea = new ImagePanel("images/background.jpg");
    private JLabel title = new JLabel();
    private JButton studentBut = new JButton();
    private JButton teacherBut = new JButton();
    private JButton officialBut = new JButton();
    private JPanel loginArea = null;
    private Hashtable<String,String> loginMap = new Hashtable<String,String>();
    private MySqlConnector conn = new MySqlConnector();

    public static String username;
    private String password;

    public LoginIn(){
        setTitle("学生教务管理系统");
        setSize(800,600);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pane = this.getContentPane();
        pane.setLayout(new BorderLayout());
        titleArea.setLayout(new BorderLayout());
        clickArea.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 200)); // 设置按钮间隔

        titleArea.add(title, BorderLayout.SOUTH);
        titleArea.setBackground(new Color(151, 184, 225));

        title.setText("学生教务管理系统");
        title.setFont(new Font("微软雅黑",Font.BOLD,35));
        title.setHorizontalAlignment(JLabel.CENTER);

        studentBut.setText("学生端");
        teacherBut.setText("教师端");
        officialBut.setText("管理员端");

        // 设置按钮字体大小
        Font buttonFont = new Font("微软雅黑", Font.BOLD, 20);
        studentBut.setFont(buttonFont);
        teacherBut.setFont(buttonFont);
        officialBut.setFont(buttonFont);

        Dimension buttonSize = new Dimension(150, 50);
        studentBut.setPreferredSize(buttonSize);
        teacherBut.setPreferredSize(buttonSize);
        officialBut.setPreferredSize(buttonSize);

        studentBut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clickArea.setVisible(false);
                try {
                    loginArea = new LoginArea("学生端管理系统");
                    initLoginMap("学生端管理系统");
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                pane.add(loginArea);
            }
        });

        teacherBut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clickArea.setVisible(false);
                try {
                    loginArea = new LoginArea("教师端管理系统");
                    initLoginMap("教师端管理系统");
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                pane.add(loginArea);
            }
        });

        officialBut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clickArea.setVisible(false);
                try {
                    loginArea = new LoginArea("管理员端管理系统");
                    initLoginMap("管理员端管理系统");
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                pane.add(loginArea);
            }
        });

        titleArea.add(title);
        clickArea.add(studentBut);
        clickArea.add(teacherBut);
        clickArea.add(officialBut);
        pane.add(titleArea, BorderLayout.NORTH);
        pane.add(clickArea, BorderLayout.CENTER);
    }

    private void initLoginMap(String accountType_) {
        loginMap.clear();
        String table_;
        String userColLabel_;
        String pswColLabel_;
        if(accountType_.equals("学生端管理系统")){
            table_ = "Student";
            userColLabel_ = "sNo";
            pswColLabel_ = "sPassword";
        }else if(accountType_.equals("教师端管理系统")){
            table_ = "Teacher";
            userColLabel_ = "tNo";
            pswColLabel_ = "tPassword";
        }else{
            table_ = "Official";
            userColLabel_ = "oNo";
            pswColLabel_ = "oPassword";
        }
        try {
            ResultSet res = conn.sqlQuery("SELECT * FROM " + table_);
            while(res.next()){
                loginMap.put(res.getString(userColLabel_),res.getString(pswColLabel_));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private class ImagePanel extends JPanel {
        private Image backgroundImage;

        public ImagePanel(String imagePath) {
            try {
                backgroundImage = ImageIO.read(new File(imagePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private class LoginArea extends JPanel{

        private String name;
        private JTextField userInput = new JTextField(20); //文本输入框
        private JPasswordField pswInput = new JPasswordField(20); //密码输入框
        private JButton loginButton = new JButton("登录"); //登录按钮
        private JButton backButton = new JButton("返回");//返回按钮

        private JLabel userLabel = new JLabel("用户名"); //用户名标签
        private JLabel pswLabel = new JLabel("密码"); //密码标签

        public LoginArea(String name_) throws ClassNotFoundException, SQLException{
            name = name_;
            title.setText(name_);

            this.setLayout(null);
            userLabel.setFont(new Font("微软雅黑",Font.PLAIN,20));
            userLabel.setBounds(260,150,100,50);
            userInput.setBounds(350,160,200,30);
            userInput.setFont(new Font("微软雅黑",Font.PLAIN,15));

            pswLabel.setFont(new Font("微软雅黑",Font.PLAIN,20));
            pswLabel.setBounds(260,200,100,50);
            pswInput.setBounds(350,210,200,30);
            pswInput.setFont(new Font("微软雅黑",Font.PLAIN,15));

            loginButton.setFont(new Font("微软雅黑",Font.PLAIN,15));
            loginButton.setBounds(350,280,80,40);

            backButton.setFont(new Font("微软雅黑",Font.PLAIN,15));
            backButton.setBounds(450,280,80,40);

            if(name.equals("学生端管理系统")){
                titleArea.setBackground(new Color(97, 191, 225));
            }else if(name.equals("教师端管理系统")){
                titleArea.setBackground(new Color(32, 189, 43));
            }else{
                titleArea.setBackground(new Color(193, 192, 88, 255));
            }

            loginButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    username = userInput.getText();
                    password = pswInput.getText();
                    if(username.isEmpty()){
                        showMessageDialog(loginArea,"用户名为空","提示",ERROR_MESSAGE);
                        return;
                    }else if(password.isEmpty()){
                        showMessageDialog(loginArea,"密码为空","提示",ERROR_MESSAGE);
                        return;
                    }
                    if(name.equals("学生端管理系统")){
                        if(username.charAt(0) == 'S' && loginMap.containsKey(username)){
                            if(loginMap.get(username).equals(password)){
                                    loginArea.setVisible(false);
                                try {
                                    pane.add(new StudentManager(loginArea));
                                } catch (ClassNotFoundException ex) {
                                    throw new RuntimeException(ex);
                                } catch (SQLException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }else{
                                showMessageDialog(loginArea,"密码错误","提示",ERROR_MESSAGE);
                            }
                        }else{
                            showMessageDialog(loginArea,"用户名错误","提示",ERROR_MESSAGE);
                        }
                    }else if(name.equals("教师端管理系统")){
                        if(username.charAt(0) == 'T' && loginMap.containsKey(username)){
                            if(loginMap.get(username).equals(password)){
                                loginArea.setVisible(false);
                                try {
                                    pane.add(new TeacherManager(loginArea));
                                } catch (ClassNotFoundException ex) {
                                    throw new RuntimeException(ex);
                                } catch (SQLException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }else{
                                showMessageDialog(loginArea,"密码错误","提示",ERROR_MESSAGE);
                            }
                        }else{
                            showMessageDialog(loginArea,"用户名错误","提示",ERROR_MESSAGE);
                        }
                    }else if(name.equals("管理员端管理系统")){
                        if(username.charAt(0) == 'O' && loginMap.containsKey(username)){
                            if(loginMap.get(username).equals(password)){
                                loginArea.setVisible(false);
                                pane.add(new OfficialManager(loginArea));
                            }else{
                                showMessageDialog(loginArea,"密码错误","提示",ERROR_MESSAGE);
                            }
                        }else{
                            showMessageDialog(loginArea,"用户名错误","提示",ERROR_MESSAGE);
                        }
                    }
                }
            });

            backButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    loginArea.setVisible(false);
                    clickArea.setVisible(true);
                    title.setText("学生教务管理系统");
                    titleArea.setBackground(new Color(151, 184, 225));
                }
            });

            this.add(userLabel);
            this.add(userInput);
            this.add(pswLabel);
            this.add(pswInput);
            this.add(loginButton);
            this.add(backButton);
        }
    }

}


