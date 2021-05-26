package TheGaem.Util;

import TheGaem.Entity.Entity;

public class AABB {

    private Vector2f pos;
    private float xOffset;
    private float yOffset;
    private float w;
    private float h;
    private float r;
    private int size;
    private Entity e;

    public AABB(Vector2f pos, int w, int h) {
        this.pos = pos;
        this.w = w;
        this.h = h;

        size = Math.max(w, h);
    }

    public AABB(Vector2f pos, int r) {
        this.pos = pos;
        this.r = r;

        size = r;
    }

    public Vector2f getPos() { return pos; }
    public float getRadius() { return r; }
    public float getWidth() { return w; }
    public float getHeight() { return h; }

    public void setBox(Vector2f pos, int w, int h) {
        this.pos = pos;
        this.w = w;
        this.h = h;

        size = Math.max(w, h);
    }

    public void setCircle(Vector2f pos, int r, Entity e) {
        this.pos = pos;
        this.r = r;
        this.e = e;

        size = r;
    }

    public void setWidth(float f) { this.w = f; }
    public void setHeight(float f) { this.h = f; }

    public void setXOffset(float f) { this.xOffset = f; }
    public void setYOffset(float f) { this.yOffset = f; }

    public boolean collides(AABB bBox, int dx, int dy) {
        float ax = pos.x + 32 + dx;
        float ay = pos.y + 32 + dy;
        float bx = bBox.getPos().x + 64;
        float by = bBox.getPos().y + 64;

        if(Math.abs(ax - bx) < 64) {
            if(Math.abs(ay - by) < 64) {
                return true;
            }
        }
        return false;
    }

    public boolean colCircleBox(AABB aBox) {

//        float cx = (float) (pos.getWorldVar().x + r / Math.sqrt(2) - e.getSize() / Math.sqrt(2));
//        float cy = (float) (pos.getWorldVar().y + r / Math.sqrt(2) - e.getSize() / Math.sqrt(2));
//
//        float xDelta = cx - Math.max(aBox.pos.getWorldVar().x + (aBox.getWidth() / 2), Math.min(cx, aBox.pos.getWorldVar().x));
//        float yDelta = cy - Math.max(aBox.pos.getWorldVar().y + (aBox.getWidth() / 2), Math.min(cy, aBox.pos.getWorldVar().y));
//
//        if((xDelta * xDelta + yDelta * yDelta) < ((this.r / Math.sqrt(2) * (this.r / Math.sqrt(2))))) {
//            return true;
//        }
        float cx = pos.x - Vector2f.worldX;
        float cy = pos.y - Vector2f.worldY;

        float xDelta = cx - aBox.getPos().x;
        float yDelta = cy - aBox.getPos().y;

        if(Math.abs(xDelta) < this.r) {
            return Math.abs(yDelta) < this.r;
        }
        return false;
    }
}
