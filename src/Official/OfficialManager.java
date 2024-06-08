package Official;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OfficialManager extends JPanel {

    //管理员端主页面

    private JPanel father = null;
    private JPanel me = this;
    private JButton messageButton = new JButton("个人信息");
    private JButton setStudentButton = new JButton("学生管理");
    private JButton setTeacherButton = new JButton("教师管理");
    private JButton setCourseButton = new JButton("课程管理");

    private MessageOperator messageOperator = new MessageOperator();
    private SetTeacherOperator setTeacherOperator = new SetTeacherOperator();
    private SetStudentOperator setStudentOperator = new SetStudentOperator();
    private SetCourseOperator setCourseOperator = new SetCourseOperator();
    private JButton backButton = new JButton("返回");


    public OfficialManager(JPanel father_){
        this.father = father_;
        this.setLayout(null);
        backButton.setBounds(20,20,70,30);
        messageButton.setBounds(200,20,100,40);
        setStudentButton.setBounds(350,20,100,40);
        setTeacherButton.setBounds(500,20,100,40);
        setCourseButton.setBounds(650,20,100,40);

        messageButton.setFont(new Font("微软雅黑",Font.PLAIN,15));
        setStudentButton.setFont(new Font("微软雅黑",Font.PLAIN,15));
        setTeacherButton.setFont(new Font("微软雅黑",Font.PLAIN,15));
        setCourseButton.setFont(new Font("微软雅黑",Font.PLAIN,15));

        messageOperator.setVisible(true);


        this.add(backButton);
        this.add(messageButton);
        this.add(setStudentButton);
        this.add(setTeacherButton);
        this.add(setCourseButton);
        this.add(messageOperator);
        this.add(setStudentOperator);
        this.add(setTeacherOperator);
        this.add(setCourseOperator);

        //返回
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                me.setVisible(false);
                father.setVisible(true);
            }
        });

        //个人信息
        messageButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                messageOperator.setVisible(true);
                setStudentOperator.setVisible(false);
                setTeacherOperator.setVisible(false);
                setCourseOperator.setVisible(false);
            }
        });

        //学生管理
        setStudentButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                messageOperator.setVisible(false);
                setStudentOperator.setVisible(true);
                setTeacherOperator.setVisible(false);
                setCourseOperator.setVisible(false);
            }
        });

        //教师管理
        setTeacherButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                messageOperator.setVisible(false);
                setStudentOperator.setVisible(false);
                setTeacherOperator.setVisible(true);
                setCourseOperator.setVisible(false);
            }
        });

        //课程管理
        setCourseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                messageOperator.setVisible(false);
                setStudentOperator.setVisible(false);
                setTeacherOperator.setVisible(false);
                setCourseOperator.setVisible(true);
            }
        });
    }
}
