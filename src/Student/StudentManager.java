package Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class StudentManager extends JPanel{

    private JPanel father = null;
    private JPanel me = this;
    private JButton messageButton = new JButton("查询/修改个人信息");
    private JButton pickCourseButton = new JButton("学生选课");
    private JButton queryScoreButton = new JButton("查询成绩");
    private JPanel messageOperator = new MessageOperator();
    private JPanel pickCourseOperator = new PickCourseOperator();
    private JPanel queryScoreOperator = new queryScoreOperator();
    private JButton backButton = new JButton("返回");

    public StudentManager(JPanel father_) throws ClassNotFoundException, SQLException{
        this.father = father_;
        this.setLayout(null);
        backButton.setBounds(20,20,70,30);
        messageButton.setBounds(180,20,200,40);
        pickCourseButton.setBounds(450,20,100,40);
        queryScoreButton.setBounds(600,20,100,40);

        messageButton.setFont(new Font("微软雅黑",Font.PLAIN,15));
        pickCourseButton.setFont(new Font("微软雅黑",Font.PLAIN,15));
        queryScoreButton.setFont(new Font("微软雅黑",Font.PLAIN,15));

        messageOperator.setVisible(true);

        this.add(queryScoreOperator);
        this.add(pickCourseOperator);
        this.add(messageOperator);
        this.add(backButton);
        this.add(messageButton);
        this.add(pickCourseButton);
        this.add(queryScoreButton);

        //返回
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                me.setVisible(false);
                father.setVisible(true);
            }
        });

        //查询并修改个人信息
        messageButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pickCourseOperator.setVisible(false);
                queryScoreOperator.setVisible(false);
                messageOperator.setVisible(true);
            }
        });

        //学生选课
        pickCourseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                messageOperator.setVisible(false);
                queryScoreOperator.setVisible(false);
                pickCourseOperator.setVisible(true);
            }
        });

        //查询成绩
        queryScoreButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                messageOperator.setVisible(false);
                pickCourseOperator.setVisible(false);
                queryScoreOperator.setVisible(true);
            }
        });
    }

}
