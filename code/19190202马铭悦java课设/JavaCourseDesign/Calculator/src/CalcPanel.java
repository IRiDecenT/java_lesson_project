import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.Border;


public class CalcPanel extends JPanel {

    private JPanel panel = null;
    private Font btnFont = null;
    private JTextField formula = null;
    private JTextField result = null;
    private TextArea log = null;
    private String nowButton = null;


    public CalcPanel() {
        setLayout(new BorderLayout());
        btnFont = new Font("微软雅黑", Font.PLAIN, 20); // 设置字体
        Font formulaFont = new Font("微软雅黑",Font.PLAIN,50);
        Font resultFont = new Font("微软雅黑",Font.PLAIN,40);

        // 添加文本框
        JPanel display = new JPanel();
        display.setLayout(new GridLayout(2,1));

        formula = new JTextField("");
        formula.setEnabled(false);
        formula.setFont(formulaFont);
        formula.setDisabledTextColor(Color.BLACK);
        formula.setBorder(null);
        formula.setBackground(new Color(242,242,242));
        display.add(formula);

        result = new JTextField("");
        result.setEnabled(false);
        result.setFont(resultFont);
        result.setDisabledTextColor(Color.GRAY);
        result.setBorder(null);
        result.setBackground(new Color(242,242,242));
        display.add(result);

        add(display, BorderLayout.NORTH);

        // 添加历史记录
        log = new TextArea(500, 30);
        log.setEnabled(false);
        log.setText("历史记录:\n");


        ActionListener command = new CommandAction();


        panel = new JPanel();

        panel.setLayout(new GridLayout(6, 4)); // panel为网格布局 8行 4列

        // 设置按钮
        addButton("π", command);
        addButton("!", command);
        addButton("C", command);
        addButton("Back", command);

        addButton("√", command);
        addButton("^", command);
        addButton("(", command);
        addButton(")", command);

        addButton("7", command);
        addButton("8", command);
        addButton("9", command);
        addButton("+", command);

        addButton("4", command);
        addButton("5", command);
        addButton("6", command);
        addButton("-", command);

        addButton("1", command);
        addButton("2", command);
        addButton("3", command);
        addButton("×", command);

        addButton(".", command);
        addButton("0", command);
        addButton("=", command);
        addButton("÷", command);
        // 将按钮添加到CENTER
        add(panel, BorderLayout.CENTER);
    }

    public TextArea getLog() {
        return log;
    }

    public void clearLog() {
        log.setText("历史记录:\n");
    }

    // 添加button 添加事件
    public void addButton(String label, ActionListener listener) {
        Color color;
        JButton button = new JButton(label);
        button.setFont(btnFont); // 设置按钮字体
        if(Character.isDigit(label.charAt(0))) {
            color=new Color(252, 252, 252);
            button.setBackground(color);
            button.setFont(new Font("微软雅黑", Font.BOLD, 20));
        } else if("=".equals(label)){
            color=new Color(138, 186, 224);
            button.setBackground(color);
        } else {
            color=new Color(248,248,248);
            button.setBackground(color);
        }
        Border border = BorderFactory.createLineBorder(new Color(242,242,242),1);
        button.setBorder(border);
        button.addActionListener(listener); // 添加监听器
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(new Color(167,167,167));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(167,167,167));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }

        });

        panel.add(button);
    }

    // 单击按钮执行命令的监听器
    class CommandAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            // 获得产生事件的按钮名称
            nowButton = event.getActionCommand();

            // 将按钮打印到display中
            if (!nowButton.equals("Back") && !nowButton.equals("=") ) {
                if(!"".equals(result.getText())) {
                    if (Character.isDigit(nowButton.charAt(0))) {
                        formula.setText("");
                    } else {
                        formula.setText(result.getText().substring(1));
                    }
                    result.setText("");
                }
                formula.setText(formula.getText() + nowButton);
            }

            if (nowButton.equals("=")) {
                // 如果是等于号，就计算结果 并打印日志
                if(CalcMathUtils.calcString(formula.getText())==null){
                    formula.setText("");
                    result.setText("");
                }
                else {
                    log.append(formula.getText() + "\n" + CalcMathUtils.calcString(formula.getText()) + "\n");
                    result.setText('=' + CalcMathUtils.calcString(formula.getText()));
                }
            }

            if (nowButton.equals("Back")) {
                // 回退一个字符
                StringBuffer sb = new StringBuffer(formula.getText());
                if (sb.length() != 0) {
                    formula.setText(sb.substring(0, sb.length() - 1));
                }
            }
            if (nowButton.equals("C")) {
                // 清空
                formula.setText("");
                result.setText("");
            }
        }
    }

}
