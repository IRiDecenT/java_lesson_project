import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class CalcFrame extends JFrame {

    private JMenuBar bar;
    private JMenu[] menus;
    private HistoryDialog dialog;
    CalcPanel jPanel;

    public CalcFrame() {
        this.setTitle("计算器");
        this.setSize(400, 600);
        this.setResizable(true);
        jPanel = new CalcPanel();
        this.add(jPanel, BorderLayout.CENTER);
        this.setVisible(true);

        this.dialog=new HistoryDialog(this,"历史记录");
        this.dialog.setModal(true);

        // 添加菜单项
        bar = new JMenuBar();
        menus = new JMenu[]{new JMenu("≡"), new JMenu("历史记录")};
        bar.add(menus[0]);
        bar.add(menus[1]);

        JMenuItem item1 = new JMenuItem("关于");
        item1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "19190202马铭悦", "", JOptionPane.DEFAULT_OPTION);
            }
        });
        JMenuItem item2 = new JMenuItem("打开");
        item2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.setTextArea(jPanel.getLog());
                dialog.setVisible(true);
            }
        });
        JMenuItem item3 = new JMenuItem("清除");
        item3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jPanel.clearLog();
                JOptionPane.showMessageDialog(null, "清除成功", "", JOptionPane.DEFAULT_OPTION);
            }
        });

        menus[0].add(item1);
        menus[1].add(item2);
        menus[1].add(item3);

        menus[0].setFont(new Font("微软雅黑",1,20));
        menus[1].setFont(new Font("微软雅黑",1,20));
        item1.setFont(new Font("微软雅黑",1,15));
        item2.setFont(new Font("微软雅黑",1,15));
        item3.setFont(new Font("微软雅黑",1,15));

        this.setJMenuBar(bar);
    }

}

