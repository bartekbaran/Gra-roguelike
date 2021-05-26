package TheGaem.Entity;

import TheGaem.Graphics.Animation;
import TheGaem.Graphics.Sprite;
import TheGaem.Util.AABB;
import TheGaem.Util.KeyHandler;
import TheGaem.Util.MouseHandler;
import TheGaem.Util.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {

    private final int UP = 3;
    private final int DOWN = 2;
    private final int RIGHT = 0;
    private final int LEFT = 1;
    private final int width = 1280;
    private final int height = 720;
    protected int currentAnimation;

    protected Animation ani;
    protected Sprite sprite;
    protected Vector2f pos;
    protected int size;

    protected boolean menu;
    protected boolean up;
    protected boolean down;
    protected boolean right;
    protected boolean left;
    protected boolean attack;
    protected int attackSpeed;
    protected int attackDuration;

    protected float dx;
    protected float dy;

    protected float maxSpeed = (float) 2.0;
    protected float acc = (float) 1.0;
    protected float deacc = (float) 1.0;

    protected int lightRadius;
    protected int damage;
    protected int level;
    protected int armor;
    protected int hp;
    protected int maxHp;

    protected AABB hitBounds;
    protected AABB bounds;

    public Entity(Sprite sprite, Vector2f orgin, int size) {
        this.sprite = sprite;
        pos = orgin;
        this.size = size;
        bounds = new AABB(orgin, size, size);
        hitBounds = new AABB(new Vector2f(orgin.x + (size / 2), orgin.y + (size / 2)), size, size);

        ani = new Animation();
        setAnimation(RIGHT, sprite.getSpirteArray(RIGHT), 100);
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setSize(int i) { this.size = i; }
    public void setMaxSpeed(float f) { this.maxSpeed = f; }
    public void setAcc(float f) { this.acc = f; }
    public void setDeacc(float f) { this.deacc = f; }

    public AABB getBounds() { return bounds; }

    public int getSize() { return size; }
    public Animation getAnimation() { return ani; }

    public void setAnimation(int i, BufferedImage[] frames, int delay) {
        currentAnimation = i;
        ani.setFrames(frames);
        ani.setDelay(delay);
    }

    public void animate() {
        if(up) {
            if(currentAnimation != UP || ani.getDelay() == -1) {
                setAnimation(UP, sprite.getSpirteArray(UP), 10);
            }
        } else if(down) {
            if(currentAnimation != DOWN || ani.getDelay() == -1) {
                setAnimation(DOWN, sprite.getSpirteArray(DOWN), 10);
            }
        } else if(left) {
            if(currentAnimation != LEFT || ani.getDelay() == -1) {
                setAnimation(LEFT, sprite.getSpirteArray(LEFT), 10);
            }
        } else if(right) {
            if(currentAnimation != RIGHT || ani.getDelay() == -1) {
                setAnimation(RIGHT, sprite.getSpirteArray(RIGHT), 10);
            }
        } else {
            setAnimation(currentAnimation, sprite.getSpirteArray(currentAnimation), 10);
        }
    }

    public void update() {
        animate();
        setHitBoxDirection();
        ani.update();
    }

    private void setHitBoxDirection() {
        if(up) {
            hitBounds.setYOffset(-size / 2);
            hitBounds.setXOffset(-size / 2);
        } else if(down) {
            hitBounds.setYOffset(size / 2);
            hitBounds.setXOffset(-size / 2);
        } else if(left) {
            hitBounds.setYOffset(-size);
            hitBounds.setXOffset(0);
        } else if(right) {
            hitBounds.setYOffset(0);
            hitBounds.setXOffset(0);
        }
    }

    public abstract void render(Graphics2D g);
    public void input(KeyHandler key, MouseHandler mouse) {}
    public void setDx(float dx) { this.dx = dx; }
    public void setDy(float dy) { this.dy = dy; }
    public float getAcc() { return acc; }
    public float getDeacc() { return deacc; }

}
