import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Frame extends JFrame implements ActionListener {
    // 常量参数
    final Font defaultFont = new Font("宋体", Font.PLAIN, 20);
    final int DEFAULT_PLATE_NUM = 3; // 默认盘子数目
    // 界面参数
    final int X_BOUND = 300;
    final int Y_BOUND = 100;
    final int WIDTH_BOUND = 1400;
    final int HEIGHT_BOUND = 680;
    // tower命名
    final int TOWER_SOURCE = 0;
    final int TOWER_HELPER = 1;
    final int TOWER_TARGET = 2;
    // movement移动路径
    final int SRC = 0;
    final int DST = 1;

    Map<String, String> buttonTextMap = new HashMap<>() {{
        put("preStep", "上一步");
        put("nextStep", "下一步");
        put("initButton", "初始化");
        put("autoButton", "自动演示");
    }};

    // 展示面板
    Panel panel;
    // 用来维护各个组件（label、button、text）的哈希表，根据Java Swing组件的继承体系，统一用JComponent管理
    Map<String, JComponent> componentMap;


    int[][] movement; // 记录移动路径
    int plateNum = DEFAULT_PLATE_NUM; // 初始时，盘子数目设置为默认值
    int curStep = 0; // 记录当前是第几步
    int totalSteps = 0; // 记录n个盘子的总步数 （2^N - 1）
    boolean isAutoRunning = false;
    Timer autoTimer;

    public Frame() {
        super("汉诺塔游戏演示");

        componentMap = new HashMap<>();
        // 初始化组件
        initComponents();

        // 主要面板放在中间
        add(panel, BorderLayout.CENTER);
        // operate menu放置到底部
        add(componentMap.get("menu"), BorderLayout.SOUTH);

        // 关闭窗口监听
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        // 设置窗口
        setBounds(X_BOUND, Y_BOUND, WIDTH_BOUND, HEIGHT_BOUND);
        setVisible(true);
        revalidate();

        // 计算保存结果的路径
        hanoi(plateNum, TOWER_SOURCE, TOWER_HELPER, TOWER_TARGET);
        curStep = 0; //计算时会修改curStep，后续交互时curStep仍需用到，需要置零

        // for debug
        for (int[] line : movement) {
            for (int i : line) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
    }

    public void initComponents() {
        totalSteps = calTotalSteps(plateNum);

        movement = new int[totalSteps][2];
        panel = new Panel(plateNum);

        loadComponents2Map();   //将组件初始化并导入哈希表
        setComponentsFont();    // 设置字体
        addButton2ActionListener(); // 监听按键
        addComponent2Menu(); // 将button，text，label加入到menu
    }

    // 将组件信息初始化并加入到哈希表中
    private void loadComponents2Map() {
        // buttons 利用一个button name 和 button text的哈希映射管理button
        for (Map.Entry<String, String> entry : buttonTextMap.entrySet()) {
            componentMap.put(entry.getKey(), new JButton(entry.getValue()));
        }

//        componentMap.put("preStep", new JButton("上一步"));
//        componentMap.put("nextStep", new JButton("下一步"));
//        componentMap.put("initButton", new JButton("初始化"));
//        componentMap.put("autoButton", new JButton("自动演示"));

        // labels
        componentMap.put("plateNumLabel", new JLabel("盘子数量:"));
        componentMap.put("stepNumLabel", new JLabel("当前步数/总步数: " + curStep + "/" + totalSteps));

        //input text
        componentMap.put("inputPlateNum", new JTextField(String.valueOf(DEFAULT_PLATE_NUM), 8));

        //operate menu
        componentMap.put("menu", new JPanel());
    }


    // 利用多态，统一设置button label text等组件字体
    private void setComponentsFont() {
        for (JComponent component : componentMap.values()) {
            component.setFont(defaultFont);
        }
    }

    private void addButton2ActionListener() {
//        JButton pre = (JButton) componentMap.get("preStep");
//        pre.addActionListener(this);
//
//        JButton next = (JButton) componentMap.get("nextStep");
//        next.addActionListener(this);
//
//        JButton init = (JButton) componentMap.get("initButton");
//        init.addActionListener(this);
//
//        JButton auto = (JButton) componentMap.get("autoButton");
//        auto.addActionListener(this);
        // 优化写法
        for (String key : buttonTextMap.keySet()) {
            JButton button = (JButton) componentMap.get(key);
            button.addActionListener(this);
        }
    }

    //将组件加入到operate menu, 布局时加入顺序决定了显示时的顺序
    private void addComponent2Menu() {
        JPanel menu = (JPanel) componentMap.get("menu");

        menu.add(componentMap.get("plateNumLabel"));

        menu.add(componentMap.get("inputPlateNum"));

        menu.add(componentMap.get("initButton"));
        menu.add(componentMap.get("autoButton"));
        menu.add(componentMap.get("preStep"));
        menu.add(componentMap.get("nextStep"));

        menu.add(componentMap.get("stepNumLabel"));

    }

    private int calTotalSteps(int n) {
        return (int) Math.pow(2, n) - 1;
    }

    // init操作
    private void doInit() {
        remove(panel);
        panel = new Panel(plateNum);
        add(panel, BorderLayout.CENTER);
        revalidate();

        movement = new int[totalSteps][2];
        curStep = 0;
        //((JLabel) componentMap.get("stepNumLabel")).setText("当前步数/总步数: " + curStep + "/" + totalSteps);
        hanoi(plateNum, TOWER_SOURCE, TOWER_HELPER, TOWER_TARGET);
        curStep = 0;
    }

    private void doNextStep() {
        panel.movePlate(movement[curStep][SRC], movement[curStep][DST]);
        ++curStep;
        //((JLabel) componentMap.get("stepNumLabel")).setText("当前步数/总步数: " + curStep + "/" + totalSteps);
    }

    private void doPreStep() {
        --curStep;
        panel.movePlate(movement[curStep][DST], movement[curStep][SRC]);
        //((JLabel) componentMap.get("stepNumLabel")).setText("当前步数/总步数: " + curStep + "/" + totalSteps);
    }

    private int getNewInputPlateNum() {
        return Integer.parseInt(((JTextField) componentMap.get("inputPlateNum")).getText());
    }

    private void refreshStepNumLabel(){
        ((JLabel) componentMap.get("stepNumLabel")).setText("当前步数/总步数: " + curStep + "/" + totalSteps);
    }

    // 实现actionPerformed接口, 交互的主体逻辑
    public void actionPerformed(ActionEvent e) {
        // 按下init键
        if (e.getSource() == componentMap.get("initButton")) {
            try {
                plateNum = getNewInputPlateNum();
                totalSteps = calTotalSteps(plateNum);
            } catch (NumberFormatException ne) {
                JOptionPane.showMessageDialog(null, "输入错误 ");
                return;
            }
            doInit();
        } else if (e.getSource() == componentMap.get("nextStep")) {
            if (curStep >= totalSteps)
                return;
            doNextStep();
        } else if (e.getSource() == componentMap.get("preStep")) {
            if (curStep <= 0)
                return;
            doPreStep();
        } else if (e.getSource() == componentMap.get("autoButton")) {

        }
        refreshStepNumLabel();
    }

    // 递归计算汉诺塔解决路径，保存在movement中
    private void hanoi(int n, int source, int helper, int target) {
        if (n == 1) {
            movement[curStep][SRC] = source;
            movement[curStep][DST] = target;
            ++curStep;
        } else {
            hanoi(n - 1, source, target, helper);
            movement[curStep][SRC] = source;
            movement[curStep][DST] = target;
            ++curStep;
            hanoi(n - 1, helper, source, target);
        }
    }

}
