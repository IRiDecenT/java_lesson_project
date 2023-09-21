import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

public class Gui implements ActionListener {
    private Font textfont = new Font("微软雅黑", Font.PLAIN, 40);
    private Font keyfont = new Font("Consolas", Font.PLAIN, 25); //按钮字体大小

    private JTextField txtdisplay;

    private String[] numkey = {
            "(", ")", "^",
            "7", "8", "9",
            "4", "5", "6",
            "1", "2", "3",
            "0", ".", "C",
    };
    private String[] opkey ={
            "/", "*", "-", "+", "=",
    };
    private JButton opkeyBut[] = new JButton[opkey.length];
    private JButton numkeyBut[] = new JButton[numkey.length];


    public void Init() {
        //frame框架
        JFrame frame = new JFrame("计算器");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(480, 700);
        frame.setResizable(false);

        initLayout(frame);
        initActionEvent();

        frame.setVisible(true);						//窗体可见
        frame.setLocationRelativeTo(null);          //窗口居中
    }

    public void initLayout(JFrame frame){               //初始化布局
        JPanel numkeypl = new JPanel();

        numkeypl.setLayout(new GridLayout(5, 3, 3, 3));		//行，列，水平， 垂直间隙
        for (int i = 0; i < numkey.length; i++) {
            numkeyBut[i] = new JButton(numkey[i]);   		//遍历numkey数组
            numkeypl.add(numkeyBut[i]);
            numkeyBut[i].setForeground(Color.BLACK);
            numkeyBut[i].setFont(keyfont);
            numkeyBut[i].setBackground(new Color(250,250,250));
            numkeyBut[i].addMouseListener(numkeyMevent(numkeyBut[i]));
        }
        for (int i = 0; i < opkey.length; i++) {
            opkeyBut[i] = new JButton(opkey[i]);
            opkeyBut[i].setForeground(Color.BLACK);
            opkeyBut[i].setFont(keyfont);
            if(opkey[i].equals("=")) {
                opkeyBut[i].setBackground(new Color(255,208,70));
                opkeyBut[i].addMouseListener(equlaskeyMevent(opkeyBut[i]));
            }else{
                opkeyBut[i].setBackground(new Color(238,238,238));
                opkeyBut[i].addMouseListener(opkeyMevent(opkeyBut[i]));
            }

        }

        JTextField text = inboxpl();

        JPanel opkeypl = new JPanel();
        opkeypl.setLayout(new GridBagLayout());  //网格袋布局

        GridBagConstraints gbc= new GridBagConstraints();//定义一个网格袋约束
        gbc.fill = GridBagConstraints.BOTH; 	// 当格子有剩余空间时，填充空间
        gbc.insets = new Insets(2, 3, 1, 3);	// 组件彼此的间距

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.ipadx = 100;     //按钮宽度
        gbc.ipady = 50;      //按钮高度
        opkeypl.add(opkeyBut[0], gbc);//   “/”

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.ipadx = 100;
        gbc.ipady = 50;
        opkeypl.add(opkeyBut[1], gbc);//	 "*"

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.ipadx = 100;
        gbc.ipady = 50;
        opkeypl.add(opkeyBut[2], gbc);//   “-”

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.ipadx = 100;
        gbc.ipady = 50;
        opkeypl.add(opkeyBut[3], gbc);//   "+"

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.ipadx = 100;
        gbc.ipady = 99;
        opkeypl.add(opkeyBut[4], gbc);//   "="

        frame.getContentPane().add("Center", numkeypl);
        frame.getContentPane().add("East", opkeypl);
        frame.getContentPane().add("North", text);
    }

    public JTextField inboxpl() {
        txtdisplay = new JTextField();
        txtdisplay.setFont(textfont);
        txtdisplay.setHorizontalAlignment(txtdisplay.RIGHT);
        txtdisplay.setPreferredSize(new Dimension(0, 150));

        return txtdisplay;
    }

    public void initActionEvent() {                    //遍历两个数组添加监听事件
        for(int i=0;i<numkey.length;i++){
            numkeyBut[i].addActionListener(this);
        }
        for(int i=0;i<opkey.length;i++){
            opkeyBut[i].addActionListener(this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String but = event.getActionCommand();
        if (Main.checkF(txtdisplay.getText(), but)) {
            txtdisplay.setText(txtdisplay.getText() + but);

            String result = Main.buttoneven(txtdisplay.getText(), but);
            if (result != "-1") {
                txtdisplay.setText(result);
            }
        }
    }

    public MouseListener numkeyMevent(JButton but) {
        MouseListener ml = new MouseListener() {

            @Override
            public void mouseEntered(MouseEvent e) {

                but.setBackground(new Color(230,230,230));        //鼠标经过
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub
                but.setBackground(new Color(250,250,250));   //鼠标离开
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub

            }

        };
        return ml;
    }
    public MouseListener opkeyMevent(JButton but) {
        MouseListener ml= new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub
                but.setBackground(new Color(238,238,238));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub
                but.setBackground(new Color(215,215,215));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub

            }
        };
        return ml;
    }
    public MouseListener equlaskeyMevent(JButton but) {
        MouseListener ml=new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub
                but.setBackground(new Color(255,208,70));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub
                but.setBackground(new Color(238,238,238));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub

            }
        };
        return ml;
    }
}