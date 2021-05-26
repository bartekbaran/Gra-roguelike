package TheGaem.Entity;

import TheGaem.Graphics.Sprite;
import TheGaem.Items.Armor;
import TheGaem.Items.Item;
import TheGaem.Items.Lantern;
import TheGaem.Items.Weapon;
import TheGaem.Util.KeyHandler;
import TheGaem.Util.MouseHandler;
import TheGaem.Util.Vector2f;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Player extends Entity {

    private Random r;
    private static int Xlen;
    private static int Ylen;
    private boolean colUP, colDOWN, colLEFT, colRIGHT;
    private boolean menuPressed;
    private ArrayList<Item> backpack;
    private int bufforX, bufforY;

    public Player(Sprite sprite, Vector2f orgin, int size) {
        super(sprite, orgin, size);
        colUP = false; colDOWN = false; colLEFT = false; colRIGHT = false;
        r = new Random();
        backpack = new ArrayList<>();
        backpack.add(new Weapon(new Sprite("Items/weapon.png"), new Vector2f(100, 200), 96));
        backpack.add(new Armor(new Sprite("Items/armor.png"), new Vector2f(100, 100), 96));
        backpack.add(new Lantern(new Sprite("Items/lantern.png"), new Vector2f(100, 100), 96));
        bounds.setBox(new Vector2f(640 + size / 3, 360 + size / 3), size /3, size / 3);
        bufforX = 0;
        bufforY = 0;
        armor = 0;
        hp = 100;
        maxHp = hp;
        level = 1;
        damage = 5;
        lightRadius = 2;
    }

    public void move() {
            if(up) {
                dy -= acc;
                if(dy < -maxSpeed) {
                    dy = -maxSpeed;
                }
            } else {
                if(dy < 0) {
                    dy += deacc;
                    if(dy > 0) {
                        dy = 0;
                    }
                }
            }

            if(down) {
                dy += acc;
                if(dy > maxSpeed) {
                    dy = maxSpeed;
                }
            } else {
                if(dy > 0) {
                    dy -= deacc;
                    if(dy < 0) {
                        dy = 0;
                    }
                }
            }

            if(left) {
                dx -= acc;
                if(dx < -maxSpeed) {
                    dx = -maxSpeed;
                }
            } else {
                if(dx < 0) {
                    dx += deacc;
                    if(dx > 0) {
                        dx = 0;
                    }
                }
            }

            if(right) {
                dx += acc;
                if(dx > maxSpeed) {
                    dx = maxSpeed;
                }
            } else {
                if(dx > 0) {
                    dx -= deacc;
                    if(dx < 0) {
                        dx = 0;
                    }
                }
            }
    }

    public void setLen(int x, int y) { Xlen = (x + 2) * 64; Ylen = (y + 2) * 64; }

    public void update() {
        super.update();
        move();
        bufforX += 2 * dx;
        bufforY += 2 * dy;
        if(bufforX >= 64) {
            Vector2f.worldX += 64;
            bufforX = 0;
        } else if(bufforX <= -64) {
            Vector2f.worldX -= 64;
            bufforX = 0;
        }
        if(bufforY >= 64) {
            Vector2f.worldY += 64;
            bufforY = 0;
        } else if(bufforY <= -64) {
            Vector2f.worldY -= 64;
            bufforY = 0;
        }
        Vector2f.playerX = 640;
        Vector2f.playerY = 360;
    }
//        if(Vector2f.playerX <= 640 && Vector2f.playerY <= 360){
//            Vector2f.worldX = 0;
//            Vector2f.worldY = 0;
//            Vector2f.playerX += dx;
//            Vector2f.playerY += dy;
//        } else if(Vector2f.playerX >= Xlen - 640 && Vector2f.playerY <= 360){
//            Vector2f.worldX = Xlen - 1280;
//            Vector2f.worldY = 0;
//            Vector2f.playerX += dx;
//            Vector2f.playerY += dy;
//        } else if(Vector2f.playerX >= Xlen - 640 && Vector2f.playerY >= Ylen - 360){
//            Vector2f.worldX = Xlen - 1280;
//            Vector2f.worldY = Ylen - 720;
//            Vector2f.playerX += dx;
//            Vector2f.playerY += dy;
//        } else if(Vector2f.playerX <= 640 && Vector2f.playerY >= Ylen - 640){
//            Vector2f.worldX = 0;
//            Vector2f.worldY = Ylen - 720;
//            Vector2f.playerX += dx;
//            Vector2f.playerY += dy;
//        } else if((Vector2f.playerX > 640 && Vector2f.playerX < Xlen - 640) && (Vector2f.playerY < 360)){
//            Vector2f.worldX = Vector2f.playerX - 640;
//            Vector2f.worldY = 0;
//            Vector2f.playerY += dy;
//            Vector2f.playerX += dx;
//        } else if((Vector2f.playerX > 640 && Vector2f.playerX < Xlen - 640) && (Vector2f.playerY > Ylen - 360)){
//            Vector2f.worldX = Vector2f.playerX - 640;
//            Vector2f.worldY = Ylen - 720;
//            Vector2f.playerY += dy;
//            Vector2f.playerX += dx;
//        } else if((Vector2f.playerY > 360 && Vector2f.playerY < Ylen - 360) && Vector2f.playerX < 640){
//            Vector2f.worldX = 0;
//            Vector2f.worldY = Vector2f.playerY - 360;
//            Vector2f.playerX += dx;
//            Vector2f.playerY += dy;
//        } else if((Vector2f.playerY > 360 && Vector2f.playerY < Ylen - 360) && Vector2f.playerX > Xlen - 640){
//            Vector2f.worldX = Xlen - 1280;
//            Vector2f.worldY = Vector2f.playerY - 360;
//            Vector2f.playerX += dx;
//            Vector2f.playerY += dy;
//        } else {
//            Vector2f.worldX = Vector2f.playerX - 640;
//            Vector2f.worldY = Vector2f.playerY - 360;
//            Vector2f.playerX += dx;
//            Vector2f.playerY += dy;
//        }
//    }

    public float getDx() { return dx; }
    public float getDy() { return dy; }
    public Vector2f getPos() { return pos; }

    public int getLightRadius() { return lightRadius; }
    public int getDamage() { return damage; }
    public int getArmor() { return armor; }
    public int getHp() { return hp; }

    public void changeDx(float x) { dx -= x; }
    public void changeDy(float y) { dy -= y; }
    public void setLightRadius(int r) { this.lightRadius = r; }
    public void setArmor(int x) { this.armor = x; }
    public void setDamage(int x) { this.damage = x; }
    public void setMaxHp(int x) { this.maxHp += x; }
    public void heal(int x) { this.hp += x; }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(ani.getImage(), (int) Vector2f.playerX, (int) Vector2f.playerY - size / 2, size, size, null);
        //g.drawRect((int) Vector2f.playerX, (int) Vector2f.playerY, size, size);
        g.setColor(Color.BLUE);
        g.drawRect((int) Vector2f.playerX + size / 4, (int) Vector2f.playerY - size / 4, size / 2, size / 2);
        g.setColor(Color.WHITE);
        if(menuPressed) {
            drawBackpack(g);
            for (Item item : backpack) {
                item.render(g);
            }
        }
        g.setColor(Color.RED);
        if(attack) {
            g.drawRect((int) pos.x, (int) pos.y, 96, 96);
        }
    }

    private void drawBackpack(Graphics2D g) {
        g.setColor(Color.WHITE);
        for(int i = 1; i < 4; i++) {
            g.drawRect(i * 90 - 10, 75, 64, 64);
        }
        for (Item item : backpack) {
            item.render(g);
        }
        g.setColor(Color.GRAY);
        g.fillRect(872,420, 312, 204);
    }

    public void addingItem(int x) {
        int random = 0;
        if(x == 1) {
            random = r.nextInt(9) + 1;
        } else if(x == 2) {
            random = r.nextInt(19) + 1;
        } else if(x == 3) {
            random = r.nextInt(3) + 3;
        }
        if(backpack.get(x - 1).getProperty() < random) {
            backpack.get(x - 1).setProperty(random);
        }
    }

    public void collidUP(int i) {
        if(i == 2) {
            colUP = true;
        } else {
            colUP = false;
        }
    }

    public void collidDOWN(int i) {
        if(i == 2) {
            colDOWN = true;
        } else {
            colDOWN = false;
        }
    }

    public void collidLEFT(int i) {
        if(i == 2) {
            colLEFT = true;
        } else {
            colLEFT = false;
        }
    }

    public void collidRIGHT(int i) {
        if(i == 2) {
            colRIGHT = true;
        } else {
            colRIGHT = false;
        }
    }

    public void input(MouseHandler mouse, KeyHandler key) {
        if(key.up.down && !colUP) {
            up = true;
        } else {
            up = false;
        }

        if(key.down.down && !colDOWN) {
            down = true;
        } else {
            down = false;
        }

        if(key.left.down && !colLEFT) {
            left = true;
        } else {
            left = false;
        }

        if(key.right.down && !colRIGHT) {
            right = true;
        } else {
            right = false;
        }

        if(key.attack.down) {
            attack = true;
        } else {
            attack = false;
        }

        if(key.menu.down) {
            menu = true;
        } else {
            menu = false;
        }

        if(key.menu.presses % 2 == 1) {
            menuPressed = true;
        } else {
            menuPressed = false;
        }

        if(mouse.getButton() == 1) {
            System.out.println("X: " + mouse.getX() + " Y: " + mouse.getY());
        }
    }
}
