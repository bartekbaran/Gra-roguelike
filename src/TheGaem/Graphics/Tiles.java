package TheGaem.Graphics;

import TheGaem.Util.AABB;
import TheGaem.Util.Vector2f;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Tiles {

    private BufferedImage TILESHEET = null;
    private final int TILESIZE = 64;
    public int w;
    public int h;
    private int wTile;
    private int hTile;
    private AABB wall;
    private Vector2f pos;

    public Tiles(String file) {
        w = TILESIZE;
        h = TILESIZE;

        System.out.println("Loading: " + file + "...");
        TILESHEET = loadTile(file);

        wall = new AABB(new Vector2f(0, 0), 64, 64);

        wTile = TILESHEET.getWidth() / w;
        hTile = TILESHEET.getHeight() / h;
    }

    public Tiles(String file, int w, int h) {
        this.w = w;
        this.h = h;

        System.out.println("Loading: " + file + "...");
        TILESHEET = loadTile(file);

        wTile = TILESHEET.getWidth() / w;
        hTile = TILESHEET.getHeight() / h;
    }

    private BufferedImage loadTile(String file) {
        BufferedImage tile = null;
        try {
            tile = ImageIO.read(getClass().getClassLoader().getResourceAsStream(file));
        } catch (Exception e) {
            System.out.println("ERROR: could not load file: " + file);
        }

        return tile;
    }

    public AABB getWall() { return wall; }
    public void setPos(Vector2f pos) {
        this.pos = pos;
    }
    public Vector2f getPos() { return pos;}

    public void isWall(Vector2f pos) {
        wall.setBox(new Vector2f(pos.x, pos.y), 64, 64);
        this.pos = pos;
    }

    public BufferedImage getTile(int x, int y) {
        BufferedImage img = TILESHEET.getSubimage(x * w, y * h, w, h);
        return img;
    }

    public BufferedImage getCoordinates(int i) {
        int x = i % wTile;
        int y = i % hTile;

        return getTile(x, y);
    }
}
