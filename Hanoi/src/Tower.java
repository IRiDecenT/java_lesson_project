import java.awt.*;
import java.util.Stack;

public class Tower extends Canvas {
    // 用栈来维护每一个柱子上的盘子，由于每次都只能移动最上面的盘子，类似栈
    Stack<Plate> plates;
    Color color;

    Tower(Color c) {
        color = c;
        plates = new Stack<>();
    }


    public void addPlate(Plate plate) {
        plates.push(plate);
    }

    public Plate movePlate() {
        return plates.pop();
    }

    public void paint(Graphics g) {
        g.setColor(color);
        g.fillRect((getSize().width) / 2 - 5, (int) (getSize().height * 0.1), 10, (int) (getSize().height * 0.8));
        g.fillRect((getSize().width) / 10, (int) (getSize().height * 0.85 + 20), (getSize().width) / 5 * 4, (int) (getSize().height * 0.06));

        for (int i = 0; i < plates.size(); i++) {
            g.setColor(plates.get(i).color);
            plates.get(i).setLocation(new Point((getSize().width) / 2, (int) (getSize().height * 0.85 - i * 20)));
            plates.get(i).Draw(g);
        }
    }

}
