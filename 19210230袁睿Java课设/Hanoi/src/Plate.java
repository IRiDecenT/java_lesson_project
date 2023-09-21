import java.awt.*;
import java.util.Random;

public class Plate {
    Point location;
    int height;
    Color color;

    Plate(int n) {
        height = n;
        Random rand = new Random();
        // 随机颜色
        color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
    }

    public void setLocation(Point p) {
        location = p;
    }

    public void Draw(Graphics g) {
        g.fillRect(location.x - (40 + height * 30) / 2, location.y, 40 + height * 30, 20);
    }
}
