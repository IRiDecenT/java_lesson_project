package calculator;

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
        btnFont = new Font("΢���ź�", Font.PLAIN, 20); // ��������
        Font formulaFont = new Font("΢���ź�",Font.PLAIN,50);
        Font resultFont = new Font("΢���ź�",Font.PLAIN,40);

        // ����ı���
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

        // �����ʷ��¼
        log = new TextArea(500, 30);
        log.setEnabled(false);
        log.setText("��ʷ��¼:\n");


        ActionListener command = new CommandAction();


        panel = new JPanel();

        panel.setLayout(new GridLayout(6, 4)); // panelΪ���񲼾� 8�� 4��

        // ���ð�ť
        addButton("��", command);
        addButton("!", command);
        addButton("C", command);
        addButton("Back", command);

        addButton("��", command);
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
        addButton("��", command);

        addButton(".", command);
        addButton("0", command);
        addButton("=", command);
        addButton("��", command);
        // ����ť��ӵ�CENTER
        add(panel, BorderLayout.CENTER);
    }

    public TextArea getLog() {
        return log;
    }

    public void clearLog() {
        log.setText("��ʷ��¼:\n");
    }

    // ���button ����¼�
    public void addButton(String label, ActionListener listener) {
        Color color;
        JButton button = new JButton(label);
        button.setFont(btnFont); // ���ð�ť����
        if(Character.isDigit(label.charAt(0))) {
            color=new Color(252, 252, 252);
            button.setBackground(color);
            button.setFont(new Font("΢���ź�", Font.BOLD, 20));
        } else if("=".equals(label)){
            color=new Color(138, 186, 224);
            button.setBackground(color);
        } else {
            color=new Color(248,248,248);
            button.setBackground(color);
        }
        Border border = BorderFactory.createLineBorder(new Color(242,242,242),1);
        button.setBorder(border);
        button.addActionListener(listener); // ��Ӽ�����
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

    // ������ťִ������ļ�����
    class CommandAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            // ��ò����¼��İ�ť����
            nowButton = event.getActionCommand();

            // ����ť��ӡ��display��
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
                // ����ǵ��ںţ��ͼ����� ����ӡ��־
                if(MathUtils.calcString(formula.getText())==null){
                    formula.setText("");
                    result.setText("");
                }
                else {
                    log.append(formula.getText() + "\n" + MathUtils.calcString(formula.getText()) + "\n");
                    result.setText('=' + MathUtils.calcString(formula.getText()));
                }
            }

            if (nowButton.equals("Back")) {
                // ����һ���ַ�
                StringBuffer sb = new StringBuffer(formula.getText());
                if (sb.length() != 0) {
                    formula.setText(sb.substring(0, sb.length() - 1));
                }
            }
            if (nowButton.equals("C")) {
                // ���
                formula.setText("");
                result.setText("");
            }
        }
    }

}
