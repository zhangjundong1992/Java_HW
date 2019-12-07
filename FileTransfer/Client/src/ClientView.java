import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ClientView extends JFrame {
    private static final Font FONT = new Font("楷体", Font.PLAIN, 18);

    private ClickDelegate delegate;

    public void setDelegate(ClickDelegate delegate) {
        this.delegate = delegate;
    }

    ClientView() {
        init();
    }

    private void init() {
        setTitle("文件传输客户端");
        setBounds(600, 300, 500, 350);
        setResizable(false);
        setLayout(null);/*绝对布局*/

        /*左侧*/
        JLabel label1 = new JLabel("IP地址：");
        label1.setBounds(50, 50, 100, 30);
        label1.setFont(FONT);
        this.add(label1);

        JLabel label2 = new JLabel("文件路径：");
        label2.setBounds(50, 100, 100, 30);
        label2.setFont(FONT);
        this.add(label2);

        JLabel label3 = new JLabel("传输结果：");
        label3.setBounds(50, 150, 100, 30);
        label3.setFont(FONT);
        this.add(label3);

        /*右侧*/
        /*转出帐号*/
        JTextField jtf1 = new JTextField();
        jtf1.setBounds(160, 50, 250, 30);
        jtf1.setFont(FONT);
        this.add(jtf1);

        /*转入帐号*/
        JTextField jtf2 = new JTextField();
        jtf2.setBounds(160, 100, 250, 30);
        jtf2.setFont(FONT);
        this.add(jtf2);

        /*转账结果*/
        JLabel label = new JLabel();
        label.setBounds(160, 150, 250, 30);
        label.setFont(FONT);
        this.add(label);

        /*底部按钮*/
        JButton btn1 = new JButton("确定");
        btn1.setBounds(100, 220, 100, 30);
        btn1.setFont(new Font("楷体", Font.PLAIN, 15));
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String ip = jtf1.getText();
                String path = jtf2.getText();
                int res = delegate.OnClick(ip, path);

                if (res == 0) {
                    label.setText("传输成功");
                }
                else if (res == -1) {
                    label.setText("传输失败");
                }
                else {
                    label.setText("异常");
                }
            }
        });
        this.add(btn1);

        JButton btn2 = new JButton("取消");
        btn2.setBounds(300, 220, 100, 30);
        btn2.setFont(new Font("楷体", Font.PLAIN, 15));
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                /*todo,暂时没有功能*/
                System.exit(0);
            }
        });
        this.add(btn2);

        setVisible(true);
    }

}
