package TheGaem.Items;

import TheGaem.Graphics.Sprite;
import TheGaem.Util.Vector2f;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Lantern extends Item {

    private int radius;
    private final String file = "Items/lantern.png";

    public Lantern(Sprite sprite, Vector2f orgin, int size) {
        super(sprite, orgin, size);
        IDnum = 3;
        radius = 3;
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(file));
        } catch (IOException e) {
            System.out.println("ERROR: could not load file: " + file);
        }
    }

    @Override
    public void setProperty(int x) { this.radius = x; }

    @Override
    public int getProperty() { return radius; }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(image, (int) pos.x, (int) pos.y, size, size, null);
    }
}
