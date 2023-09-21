import javax.swing.*;
import java.awt.*;

public class HanoiPanel extends JPanel {
    Tower[] towers = new Tower[3];
    JButton bt;

    HanoiPanel(int n)
    {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        towers[0] = new Tower(n, Color.black);
        towers[1] = new Tower(n, Color.black);
        towers[2] = new Tower(n, Color.black);
        for (int i = n - 1; i >= 0; i--)
        {
            towers[0].addPlate(new Plate(i));
        }
        add(towers[0]);
        add(Box.createHorizontalStrut(10));
        add(towers[1]);
        add(Box.createHorizontalStrut(10));
        add(towers[2]);

        validate();
    }
    public void paint(Graphics g)
    {
        g.drawRect(10, 20, 100, 110);
        towers[0].repaint();
        towers[1].repaint();
        towers[2].repaint();
    }
    public void movePlate(int a, int b)
    {
        Plate plate = towers[a].movePlate();
        towers[b].addPlate(plate);
        repaint();
    }

}
