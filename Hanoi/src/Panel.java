import javax.swing.*;
import java.awt.*;

// 自己定制继承自JPanel的面板
public class Panel extends JPanel {
    final int TOWER_NUM = 3;
    Tower[] towers = new Tower[TOWER_NUM];

    Panel(int plateNum) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        for (int i = 0; i < TOWER_NUM; i++)
            towers[i] = new Tower(Color.black);

        // 将所有盘子加入到第一个根柱子上
        for (int i = plateNum - 1; i >= 0; i--) {
            towers[0].addPlate(new Plate(i));
        }
        add(towers[0]);
        add(Box.createHorizontalStrut(10));
        add(towers[1]);
        add(Box.createHorizontalStrut(10));
        add(towers[2]);

        revalidate();
    }

    public void paint(Graphics g) {
        g.drawRect(10, 20, 100, 110);
        for(Tower t : towers){
            t.repaint();
        }
    }

    public void movePlate(int src, int dst) {
        Plate plate = towers[src].movePlate();
        towers[dst].addPlate(plate);
        repaint();
    }

}
