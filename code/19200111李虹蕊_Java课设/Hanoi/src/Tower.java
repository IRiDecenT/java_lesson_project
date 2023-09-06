import java.awt.*;

public class Tower extends Canvas {
    Plate[] plates;
    int plateNum;
    Color color;
    Tower(int n, Color c)
    {
        plateNum = 0;
        color = c;
        plates = new Plate[n];
    }
    public void addPlate(Plate plate)
    {
        plates[plateNum] = plate;
        plateNum++;
    }
    public Plate movePlate()
    {
        plateNum--;
        Plate plate = plates[plateNum];
        plates[plateNum] = null;
        return plate;
    }
    public void paint(Graphics g)
    {
        g.setColor(color);
        g.fillRect((getSize().width) / 2 - 5, (int)(getSize().height*0.1), 10, (int)(getSize().height*0.8));
        g.fillRect((getSize().width) / 10, (int)(getSize().height*0.85 + 20), (getSize().width) / 5 * 4, (int)(getSize().height*0.06));

        for (int i = 0; i < plateNum; i++)
        {
            g.setColor(plates[i].color);
            plates[i].setLocation(new Point((getSize().width) / 2, (int)(getSize().height*0.85 - i * 20)));
            plates[i].Draw(g);
        }
    }

}
