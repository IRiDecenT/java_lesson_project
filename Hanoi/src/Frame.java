import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Frame extends JFrame implements ActionListener {
    // 常量参数
    final Font defaultFont = new Font("宋体", Font.PLAIN, 20);
    final int DEFAULT_PLATE_NUM = 3;
    final int X_BOUND = 300;
    final int Y_BOUND = 100;
    final int WIDTH_BOUND = 1400;
    final int HEIGHT_BOUND = 680;
    final int TOWER_SOURCE = 0;
    final int TOWER_HELPER = 1;
    final int TOWER_TARGET = 2;

    Panel panel;
    JButton prevStep, nextStep, initButton, autoButton;
    TextField inputPlateNum;
    JLabel plateNumLabel, stepNumLabel;
    JPanel menu;
    int[][] movement;
    int plateNum = DEFAULT_PLATE_NUM;
    int curStep;
    int totalSteps = 0;
    boolean isAutoRunning = false;

    public Frame() {
        super("汉诺塔游戏演示");

        // 初始化组件
        initComponents();
        // 主要面板放在中间
        add(panel, BorderLayout.CENTER);
        // menu放置到底部
        add(menu, BorderLayout.SOUTH);

        // 关闭窗口监听
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        setBounds(X_BOUND, Y_BOUND, WIDTH_BOUND, HEIGHT_BOUND);
        setVisible(true);
        revalidate();
        hanoi(plateNum, TOWER_SOURCE, TOWER_HELPER, TOWER_TARGET);
        curStep = 0;
    }

    public void initComponents() {
        totalSteps = calTotalSteps(plateNum);

        movement = new int[totalSteps][2];
        panel = new Panel(plateNum);

        prevStep = new JButton("上一步");
        prevStep.setFont(defaultFont);

        nextStep = new JButton("下一步");
        nextStep.setFont(defaultFont);

        initButton = new JButton("初始化");
        initButton.setFont(defaultFont);

        autoButton = new JButton("自动演示");
        autoButton.setFont(defaultFont);

        plateNumLabel = new JLabel("盘子数量:");
        plateNumLabel.setFont(defaultFont);

        inputPlateNum = new TextField(String.valueOf(DEFAULT_PLATE_NUM), 8);
        inputPlateNum.setFont(defaultFont);

        stepNumLabel = new JLabel("当前步数/总步数: " + curStep + "/" + totalSteps);
        stepNumLabel.setFont(defaultFont);

        prevStep.addActionListener(this);
        nextStep.addActionListener(this);
        initButton.addActionListener(this);
        autoButton.addActionListener(this);

        menu = new JPanel();
        menu.add(plateNumLabel);
        menu.add(inputPlateNum);
        menu.add(prevStep);
        menu.add(nextStep);
        menu.add(initButton);
        menu.add(autoButton);
        menu.add(stepNumLabel);
    }

    private int calTotalSteps(int n) {
        return (int) Math.pow(2, n) - 1;
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == initButton) {
            try {
                plateNum = Integer.parseInt(inputPlateNum.getText());
                totalSteps = calTotalSteps(plateNum);
            } catch (NumberFormatException ee) {
                JOptionPane.showMessageDialog(null, "输入错误 ");
                return;
            }
            remove(panel);
            panel = new Panel(plateNum);
            add(panel, BorderLayout.CENTER);
            revalidate();

            movement = new int[totalSteps][2];
            curStep = 0;
            stepNumLabel.setText("当前步数/总步数: " + curStep + "/" + totalSteps);
            hanoi(plateNum, TOWER_SOURCE, TOWER_HELPER, TOWER_TARGET);
            curStep = 0;

        } else if (e.getSource() == nextStep) {
            if (curStep >= totalSteps)
                return;
            panel.movePlate(movement[curStep][0], movement[curStep][1]);
            curStep++;
            stepNumLabel.setText("当前步数/总步数: " + curStep + "/" + totalSteps);
        } else if (e.getSource() == prevStep) {
            if (curStep <= 0)
                return;
            curStep--;
            panel.movePlate(movement[curStep][1], movement[curStep][0]);
            stepNumLabel.setText("当前步数/总步数: " + curStep + "/" + totalSteps);
        }else if(e.getSource() == autoButton) {

        }
    }

    private void hanoi(int n, int source, int helper, int target) {
        if (n == 1) {
            movement[curStep][0] = source;
            movement[curStep][1] = target;
            curStep++;
        } else {
            hanoi(n - 1, source, target, helper);
            movement[curStep][0] = source;
            movement[curStep][1] = target;
            curStep++;
            hanoi(n - 1, helper, source, target);
        }
    }

}
