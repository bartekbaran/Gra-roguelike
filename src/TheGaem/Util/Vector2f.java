package TheGaem.Util;

public class Vector2f {

    public float x;
    public float y;

    public static float worldX;
    public static float worldY;
    public static float playerX;
    public static float playerY;

    public Vector2f() {
        x = 0;
        y = 0;
    }

    public Vector2f(Vector2f vec) {
        new Vector2f(vec.x, vec.y);
    }

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void addX(float i) { x += i; }
    public void addY(float i) { y += i; }

    public void setX(float i) { x = i; }
    public void setY(float i) { y = i; }

    public float getX() { return x; }
    public float getY() { return y; }

    public void setVector(Vector2f vec) {
        this.x = vec.x;
        this.y = vec.y;
    }

    public void setVector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public static void setWorldVar(float x, float y) {
        worldX = x;
        worldY = y;
    }

    public Vector2f getWorldVar() {
        return new Vector2f(x - worldX, y - worldY);
    }

    @Override
    public String toString() {
        return x + ", " + y;
    }

    //custom hash key - do woreczkow
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (int) x * prime + result;
        result = (int) y * prime + result;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null) {
            return false;
        }
        if(getClass() != obj.getClass()) {
            return false;
        }
        Vector2f other = (Vector2f) obj;
        if(other.x != this.x) {
            return false;
        }
        if(other.y != this.y) {
            return false;
        }
        return true;
    }
}
