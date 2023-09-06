import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HanoiFrame extends JFrame implements ActionListener{
    HanoiPanel panel;
    JButton pre, next, init;
    TextField input;
    Label label;
    JPanel menu;
    int[][] a;
    int n = 5;
    int i = 0;
    public HanoiFrame() {
        super("汉诺塔");
        a = new int[(int) Math.pow(2, n)][2];
        panel = new HanoiPanel(n);

        pre = new JButton("上一步");
        pre.setFont(new Font("微软雅黑",1,20));
        next = new JButton("下一步");
        next.setFont(new Font("微软雅黑",1,20));
        init = new JButton("初始化");
        init.setFont(new Font("微软雅黑",1,20));
        label = new Label("盘子个数:");
        label.setFont(new Font("微软雅黑",1,20));
        input = new TextField("5", 8);
        input.setFont(new Font("微软雅黑",1,20));

        pre.addActionListener(this);
        next.addActionListener(this);
        init.addActionListener(this);

        menu = new JPanel();
        menu.add(label);
        menu.add(input);
        menu.add(pre);
        menu.add(next);
        menu.add(init);


        add(panel, BorderLayout.CENTER);
        add(menu, BorderLayout.SOUTH);


        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        setBounds(300, 100, 1200, 500);
        setVisible(true);
        validate();

        hanoi(n, 0, 1, 2);
        i = 0;
    }
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == init)
        {
            try
            {
                n = Integer.parseInt(input.getText());
            }
            catch (NumberFormatException ee)
            {
                JOptionPane.showMessageDialog(null, "输入错误 ");
                return;
            }
            remove(panel);
            panel = new HanoiPanel(n);
            add(panel, BorderLayout.CENTER);
            validate();
            a = new int[(int)Math.pow(2, n)][2];
            i = 0;
            hanoi(n, 0, 1, 2);
            i = 0;
        }
        else if (e.getSource() == next)
        {
            if (i >= (int)Math.pow(2, n) - 1)
                return;
            panel.movePlate(a[i][0], a[i][1]);
            i++;

        }
        else
        {
            if (i <= 0)
                return;
            i--;
            panel.movePlate(a[i][1], a[i][0]);
        }
    }
    private void hanoi(int n, int x, int y, int z)
    {
        if (n == 1)
        {
            a[i][0] = x;
            a[i][1] = z;
            i++;
        }
        else
        {
            hanoi(n - 1, x, z, y);
            a[i][0] = x;
            a[i][1] = z;
            i++;
            hanoi(n - 1, y, x, z);
        }
    }

}
