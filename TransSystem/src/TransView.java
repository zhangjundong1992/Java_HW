import utilities.Observer;
import utilities.ResCode;
import utilities.Subject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

class TransView extends JFrame implements Subject {
    private Observer observer;
    private JLabel label;

    TransView() {
        init();
    }

    /*初始化UI*/
    private void init() {
        /*初始化窗体*/
        setTitle("转账系统");
        setBounds(600, 300, 400, 400);
        setResizable(false);
        setLayout(null);/*绝对布局*/

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container container = getContentPane();

        /*左侧*/
        JLabel label1 = new JLabel("转出帐号");
        label1.setBounds(50, 50, 80, 30);
        label1.setFont(new Font("楷体", Font.PLAIN, 18));
        container.add(label1);

        JLabel label2 = new JLabel("转入帐号");
        label2.setBounds(50, 100, 80, 30);
        label2.setFont(new Font("楷体", Font.PLAIN, 18));
        container.add(label2);

        JLabel label3 = new JLabel("转账金额");
        label3.setBounds(50, 150, 80, 30);
        label3.setFont(new Font("楷体", Font.PLAIN, 18));
        container.add(label3);

        JLabel label4 = new JLabel("转账结果");
        label4.setBounds(50, 200, 80, 30);
        label4.setFont(new Font("楷体", Font.PLAIN, 18));
        container.add(label4);

        /*右侧*/
        /*转出帐号*/
        JTextField jtf1 = new JTextField();
        jtf1.setBounds(160, 50, 180, 30);
        jtf1.setFont(new Font("楷体", Font.PLAIN, 18));
        container.add(jtf1);

        /*转入帐号*/
        JTextField jtf2 = new JTextField();
        jtf2.setBounds(160, 100, 180, 30);
        jtf2.setFont(new Font("楷体", Font.PLAIN, 18));
        container.add(jtf2);

        /*转账金额*/
        JTextField jtf3 = new JTextField();
        jtf3.setBounds(160, 150, 180, 30);
        jtf3.setFont(new Font("楷体", Font.PLAIN, 18));
        container.add(jtf3);

        /*转账结果*/
        label = new JLabel();
        label.setBounds(160, 200, 180, 30);
        label.setFont(new Font("楷体", Font.PLAIN, 18));
        container.add(label);


        /*底部按钮*/
        JButton btn1 = new JButton("确定");
        btn1.setBounds(80, 280, 80, 30);
        btn1.setFont(new Font("楷体", Font.PLAIN, 15));
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Object user_out = jtf1.getText();
                Object user_in = jtf2.getText();
                Object count = jtf3.getText();
                Object[] arg = {user_out, user_in, count};
                notifyObserver(arg);
            }
        });
        container.add(btn1);

        JButton btn2 = new JButton("取消");
        btn2.setBounds(240, 280, 80, 30);
        btn2.setFont(new Font("楷体", Font.PLAIN, 15));
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                /*todo,暂时没有功能*/
                System.exit(0);
            }
        });
        container.add(btn2);

        setVisible(true);
    }

    /*注册回调*/
    void setObserver(Observer observer) {
        this.observer = observer;
    }

    /*回调*/
    @Override
    public void notifyObserver(Object[] arg) {
        observer.update(arg);
    }

    /*更新转账结果*/
    void ShowResult(ResCode res) {
        switch (res) {
            case SUCCESS:
                label.setText("转账成功");
                break;
            case NULL_ACCOUNT:
                label.setText("账户不存在");
                break;
            case NO_MONEY:
                label.setText("账户金额不足");
                break;
            case OTHER:
                label.setText("转账错误，联系GM");
                break;
        }
    }
}
