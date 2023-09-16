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
        this.setTitle("������");
        this.setSize(400, 600);
        this.setResizable(true);
        jPanel = new CalcPanel();
        this.add(jPanel, BorderLayout.CENTER);
        this.setVisible(true);

        this.dialog=new History(this,"��ʷ��¼");
        this.dialog.setModal(true);

        // ��Ӳ˵���
        bar = new JMenuBar();
        menus = new JMenu[]{new JMenu("��"), new JMenu("��ʷ��¼")};
        bar.add(menus[0]);
        bar.add(menus[1]);

        JMenuItem item1 = new JMenuItem("����");
        item1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "19200111�����", "", JOptionPane.DEFAULT_OPTION);
            }
        });
        JMenuItem item2 = new JMenuItem("��");
        item2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.setTextArea(jPanel.getLog());
                dialog.setVisible(true);
            }
        });
        JMenuItem item3 = new JMenuItem("���");
        item3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jPanel.clearLog();
                JOptionPane.showMessageDialog(null, "����ɹ�", "", JOptionPane.DEFAULT_OPTION);
            }
        });

        menus[0].add(item1);
        menus[1].add(item2);
        menus[1].add(item3);

        menus[0].setFont(new Font("΢���ź�",1,20));
        menus[1].setFont(new Font("΢���ź�",1,20));
        item1.setFont(new Font("΢���ź�",1,15));
        item2.setFont(new Font("΢���ź�",1,15));
        item3.setFont(new Font("΢���ź�",1,15));

        this.setJMenuBar(bar);
    }

}

