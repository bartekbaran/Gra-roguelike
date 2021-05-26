package TheGaem.Entity;

import TheGaem.Graphics.Sprite;
import TheGaem.Util.AABB;
import TheGaem.Util.Vector2f;

import java.awt.*;

public class Enemy extends Entity {

    private Vector2f pos;
    private Vector2f circVec;
    private int r;
    private float dx;
    private float dy;

    public Enemy(Sprite sprite, Vector2f orgin, int size) {
        super(sprite, orgin, size);
        r = 192;
        pos = orgin;
        setMaxSpeed(0.25F);
        setAcc(0.5F);
        circVec = new Vector2f(orgin.getX() - r/2 + size/2, orgin.getY() - r/2 + size/2);
        bounds.setCircle(circVec, r, this);
    }

    public void update() {
        super.update();
        circVec.setVector(pos.getX() - r/2 + size/2, pos.getY() - r/2 + size/2);
    }

    @Override
    public void render(Graphics2D g) {
        g.setPaint(Color.red);
        g.drawOval((int) (pos.getX() - Vector2f.worldX - size), (int) (pos.getY() - Vector2f.worldY - size), r, r);
        g.drawImage(ani.getImage(), (int) (pos.getX() - Vector2f.worldX), (int) (pos.getY() - Vector2f.worldY), size, size, null);
    }

    public void settingAni() {
        if(Math.abs(dy) >= Math.abs(dx)) {
            if(dy < 0) {
                up = true;
            } else if(dy > 0) {
                down = true;
            }
        } else {
            if(dx < 0) {
                right = true;
            } else if(dx > 0) {
                left = true;
            }
        }
    }

    public void move(AABB bBox) {
        if(bounds.colCircleBox(bBox)) {
            float currPosX = pos.x - Vector2f.worldX;
            float currPosY = pos.y - Vector2f.worldY;
            System.out.println("Pos: " + currPosX + ", " + currPosY);
            double divider = (1 / maxSpeed) * Math.sqrt(Math.pow(bBox.getPos().x - currPosX, 2) + Math.pow(bBox.getPos().y - currPosY, 2));
            dx = (float) ((bBox.getPos().x - currPosX) / divider);
            dy = (float) ((bBox.getPos().y - currPosY) / divider);
            if(dx < Math.abs(pos.getX() - bBox.getPos().x)) {
                pos.x += dx;
            } else {
                pos.x = bBox.getPos().x;
            }
            if(dy < Math.abs(pos.getY() - bBox.getPos().y)) {
                pos.y += dy;
            } else {
                pos.y = bBox.getPos().y;
            }
            settingAni();

        } else {
            up = false;
            down = false;
            left = false;
            right = false;
            dx = 0;
            dy = 0;
        }
    }

    public Vector2f getPos() { return this.pos; }
}
