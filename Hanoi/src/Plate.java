import java.awt.*;
import java.util.Random;

public class Plate {
    Point location;
    int level;
    Color color;

    Plate(int l) {
        level = l;
        Random rand = new Random();
        color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
    }

    public void setLocation(Point p) {
        location = p;
    }

    public void Draw(Graphics g) {
        g.fillRect(location.x - (40 + level * 30) / 2, location.y, 40 + level * 30, 20);
    }
}
