package calculator;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class CalcFrame extends JFrame {

    private JMenuBar bar;
    private JMenu[] menus;
    private History dialog;
    CalcPanel jPanel;

    public CalcFrame() {
        this.setTitle("¼ÆËãÆ÷");
        this.setSize(400, 600);
        this.setResizable(true);
        jPanel = new CalcPanel();
        this.add(jPanel, BorderLayout.CENTER);
        this.setVisible(true);

        this.dialog=new History(this,"ÀúÊ·¼ÇÂ¼");
        this.dialog.setModal(true);

        // Ìí¼Ó²Ëµ¥Ïî
        bar = new JMenuBar();
        menus = new JMenu[]{new JMenu("¡Ô"), new JMenu("ÀúÊ·¼ÇÂ¼")};
        bar.add(menus[0]);
        bar.add(menus[1]);

        JMenuItem item1 = new JMenuItem("¹ØÓÚ");
        item1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "19200111ÀîºçÈï", "", JOptionPane.DEFAULT_OPTION);
            }
        });
        JMenuItem item2 = new JMenuItem("´ò¿ª");
        item2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.setTextArea(jPanel.getLog());
                dialog.setVisible(true);
            }
        });
        JMenuItem item3 = new JMenuItem("Çå³ý");
        item3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jPanel.clearLog();
                JOptionPane.showMessageDialog(null, "Çå³ý³É¹¦", "", JOptionPane.DEFAULT_OPTION);
            }
        });

        menus[0].add(item1);
        menus[1].add(item2);
        menus[1].add(item3);

        menus[0].setFont(new Font("Î¢ÈíÑÅºÚ",1,20));
        menus[1].setFont(new Font("Î¢ÈíÑÅºÚ",1,20));
        item1.setFont(new Font("Î¢ÈíÑÅºÚ",1,15));
        item2.setFont(new Font("Î¢ÈíÑÅºÚ",1,15));
        item3.setFont(new Font("Î¢ÈíÑÅºÚ",1,15));

        this.setJMenuBar(bar);
    }

}

