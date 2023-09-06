package calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class History extends JDialog {

    TextArea textArea;

    public History(JFrame jFrame,String s){
        super(jFrame,s);
        setLayout(new BorderLayout());
        textArea=new TextArea(500,30);
        textArea.setFont(new Font("ËÎÌו", Font.PLAIN, 20));
        textArea.setEnabled(false);
        add(textArea,BorderLayout.CENTER);
        setBounds(600,260,300,500);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    public void setTextArea(TextArea textArea) {
        this.textArea.setText(textArea.getText());
    }

}