package Teacher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import static javax.swing.JOptionPane.showMessageDialog;

public class TeacherManager extends JPanel {

    private JPanel father = null;
    private JPanel me = this;
    private JButton messageButton = new JButton("查询/修改个人信息");
    private JButton queryTasksButton = new JButton("教学任务");
    private JButton setScoreButton = new JButton("成绩录入");
    private MessageOperator messageOperator = new MessageOperator();
    private QueryTasksOperator queryTasksOperator = new QueryTasksOperator();
    private SetScoreOperator setScoreOperator = new SetScoreOperator();
    private JButton backButton = new JButton("返回");

    public TeacherManager(JPanel father_) throws ClassNotFoundException, SQLException {
        this.father = father_;
        this.setLayout(null);
        backButton.setBounds(20,20,70,30);
        messageButton.setBounds(180,20,200,40);
        queryTasksButton.setBounds(450,20,100,40);
        setScoreButton.setBounds(600,20,100,40);

        messageButton.setFont(new Font("微软雅黑",Font.PLAIN,15));
        queryTasksButton.setFont(new Font("微软雅黑",Font.PLAIN,15));
        setScoreButton.setFont(new Font("微软雅黑",Font.PLAIN,15));

        messageOperator.setVisible(true);
        setScoreButton.setEnabled(false);

        this.add(queryTasksOperator);
        this.add(setScoreOperator);
        this.add(messageOperator);
        this.add(backButton);
        this.add(messageButton);
        this.add(queryTasksButton);
        this.add(setScoreButton);

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
                setScoreButton.setEnabled(false);
                queryTasksOperator.setVisible(false);
                setScoreOperator.setVisible(false);
                messageOperator.setVisible(true);
            }
        });

        //教学任务
        queryTasksButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setScoreButton.setEnabled(true);
                messageOperator.setVisible(false);
                setScoreOperator.setVisible(false);
                queryTasksOperator.setVisible(true);
            }
        });

        //成绩录入
        setScoreButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!setScoreButton.isEnabled()) return;
                String selectedCourseNo = queryTasksOperator.getSelectedCourseNo();
                if( selectedCourseNo.equals("NOTHING") ){
                    showMessageDialog(null,"请先选中一门课程","提示",JOptionPane.INFORMATION_MESSAGE);
                    return;
                }else {
                    setScoreOperator.setSelectedCourseNo(selectedCourseNo);
                    setScoreOperator.initStudentData();
                }
                messageOperator.setVisible(false);
                queryTasksOperator.setVisible(false);
                setScoreOperator.setVisible(true);
            }
        });
    }

}