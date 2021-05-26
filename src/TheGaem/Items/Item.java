package TheGaem.Items;

import TheGaem.Graphics.Sprite;
import TheGaem.Util.MouseHandler;
import TheGaem.Util.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public abstract class Item {

    protected BufferedImage image = null;
    protected Random r;
    protected int IDnum;
    protected Sprite sprite;
    protected Vector2f pos;
    protected int size;

    public Item(Sprite sprite, Vector2f orgin, int size) {
        this.sprite = sprite;
        pos = orgin;
        this.size = size;
    }

    public void setSprite(Sprite sprite) { this.sprite = sprite; }
    public void setSize(int size) { this.size = size; }
    public void setPosByVec(Vector2f vec) { pos = vec; }
    public void setPosByXY(float x, float y) {
        pos.setX(x);
        pos.setY(y);
    }

    public int getIDnum() { return IDnum; }

    public abstract void setProperty(int x);
    public abstract int getProperty();
    public abstract void render(Graphics2D g);
    public void input(MouseHandler mouse) {}
}
