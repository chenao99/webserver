import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class form extends JFrame implements ActionListener {

    private JButton open;
    private JButton close;
    private JFrame frame;
    static form forms;
    public WebServer web;

    public form (){
        createAndShowGUI();
    }


    private void createAndShowGUI() {
        frame = new JFrame("web");
        frame.setSize(1500,1200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        //frame.setBounds(300, 200, 1500, 1200);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 1500, 1200);
        frame.getContentPane().add(panel);
        panel.setLayout(null);
        panel.setOpaque(false);   //设置面板为透明

        open = new JButton("开启");
        open.setFont(new Font("宋体", Font.PLAIN, 25));
        open.setBounds(200, 433, 300, 150);
        panel.add(open);
        open.addActionListener(this);

        close = new JButton("关闭");
        close.setFont(new Font("宋体", Font.PLAIN, 25));
        close.setBounds(800, 433, 300, 150);
        panel.add(close);
        close.addActionListener(this);

        // 显示窗口
        //frame.pack();
        frame.setVisible(true);
    }

    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    forms = new form();
                    forms.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public class ThreadSafe extends Thread {
        public boolean exit = false;
        @Override
        public void run() {
            web=new WebServer();
            while(!exit){
                try {
                    web.start();
                } catch (IOException ee) {
                    ee.printStackTrace();
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ThreadSafe t=new ThreadSafe();

        if(e.getSource()==open){
            t.start();

        }
        else if(e.getSource()==close){
            web.stopsocket();
        }
    }
}
