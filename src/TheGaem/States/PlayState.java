package TheGaem.States;

import TheGaem.Entity.Enemy;
import TheGaem.Entity.Player;
import TheGaem.Graphics.Font;
import TheGaem.Graphics.Sprite;
import TheGaem.Graphics.Tiles;
import TheGaem.Util.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class PlayState extends GameState {

    private Enemy enemy;
    private ArrayList<Level> level;
    private ArrayList<Enemy> enemies;
    private Tiles tile;
    private Font font;
    private Player player;
    private AABB aabb;
    private Vector2f pos;
    private GameStateManager gsm;
    private String defFilePath;
    private HashMap<Vector2f, Boolean> treasure;
    private ArrayList<Vector2f> treasureArray;
    private Random r;
    private int i, counter, Xlen, Ylen;
    private int loot;
    private boolean notif = false;
    private Vector2f notifPos;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        this.gsm = gsm;
        notifPos = new Vector2f(0, 0);
        i = 0;
        loot = 0;
        r = new Random();
        treasure = new HashMap<>();
        treasureArray = new ArrayList<>();
        enemies = new ArrayList<>();
        level = new ArrayList<>();
        defFilePath = "res/Levels/level";
        player = new Player(new Sprite("Entity/linkFormatted.png"), new Vector2f(Vector2f.playerX, Vector2f.playerY), 64);
        font = new Font("Font/ZeldaFont.png", 16, 16);
        tile = new Tiles("Tilemap/tilemap.png");
        aabb = new AABB(new Vector2f(608, 328), 100);
        counter = 0;
        creatingNewLevel();
    }

    public void update() {
        player.update();
        for (Enemy value : enemies) {
            value.move(player.getBounds());
            value.update();
        }
    }

    public void input(MouseHandler mouse, KeyHandler key) {
        player.input(mouse, key);
    }

    public void render(Graphics2D graphic) {
        drawingTiles(graphic);
        for(Enemy value : enemies) {
            if(Vector2f.worldX < value.getPos().x && Vector2f.worldX + gsm.getWidth() > value.getPos().x) {
                if(Vector2f.worldY < value.getPos().y && Vector2f.worldY + gsm.getHeight() > value.getPos().y) {
                    value.render(graphic);
                }
            }
        }
        player.render(graphic);
        if(notif && counter < 100) {
            Sprite.drawArray(graphic, font, "NEW ITEM", new Vector2f(notifPos.getX() - 16, (int) notifPos.getY() - counter/2), 32, 32, 16, 0);
            counter++;
            if(counter == 100) {
                notif = false;
                counter = 0;
            }
        }
    }

    public void settingEnemies() {
        //for(int t = 0; t < level.get(i - 1).getNumberOfEnemies(); t++) {
        for(int t = 0; t < 1; t++) {
            Vector2f vec = new Vector2f((r.nextInt(level.get(i - 1).getArrayXLen() - 1) + 1) * 64, (r.nextInt(level.get(i - 1).getArrayYLen() - 1) + 1) * 64);
            enemies.add(new Enemy(new Sprite("Entity/egnok.png"), vec, 64));
        }
    }

    public void creatingNewLevel() {
        enemies.clear();
        level.add(new Level(defFilePath + i + ".txt", 4 * (i + 1), 2 * (i + 1)));
        i++;
        Xlen = (level.get(level.size() - 1).getArrayXLen() + 2) * 64;
        Ylen = (level.get(level.size() - 1).getArrayXLen() + 2) * 64;
        player.setLen(level.get(level.size() - 1).getArrayXLen(), level.get(level.size() - 1).getArrayYLen());

        // First camera settings
        Vector2f.playerX = level.get(i - 1).getSpawnX() * 64;
        Vector2f.playerY = level.get(i - 1).getSpawnY() * 64;
        Vector2f.worldX = Vector2f.playerX - 640;
        Vector2f.worldY = Vector2f.playerY - 360;

        if(Vector2f.worldX < 0) {
            Vector2f.worldX = 0;
        } else if(Vector2f.worldX + 1280 > Xlen){
            Vector2f.worldX = Xlen - 1280;
        }
        if(Vector2f.worldY < 0) {
            Vector2f.worldY = 0;
        } else if(Vector2f.worldY + 720 > Ylen) {
            Vector2f.worldY = Ylen - 720;
        }
        settingEnemies();
    }

    public void drawingTiles(Graphics2D graphic) {
        player.collidLEFT(level.get(i - 1).getTile((int) (Vector2f.playerX + Vector2f.worldX) / 64 - 2, (int) (Vector2f.playerY + Vector2f.worldY) / 64 - 1));
        player.collidRIGHT(level.get(i - 1).getTile((int) (Vector2f.playerX + Vector2f.worldX) / 64, (int) (Vector2f.playerY + Vector2f.worldY) / 64 - 1));
        player.collidUP(level.get(i - 1).getTile((int) (Vector2f.playerX + Vector2f.worldX) / 64 - 1, (int) (Vector2f.playerY + Vector2f.worldY) / 64 - 2));
        player.collidDOWN(level.get(i - 1).getTile((int) (Vector2f.playerX + Vector2f.worldX) / 64 - 1, (int) (Vector2f.playerY + Vector2f.worldY) / 64));

        Vector2f exitVec = new Vector2f();
        for(float y = 0; y <= gsm.getHeight(); y += 64) {
            //if(Vector2f.playerY + (64 * player.getLightRadius()) > y && Vector2f.playerY - (64 * player.getLightRadius()) < y) {
                for (float x = 0; x < gsm.getWidth(); x += 64) {
                    //if (Vector2f.playerX + (64 * player.getLightRadius()) > x && Vector2f.playerX - (64 * player.getLightRadius()) < x) {
                        int tileType = level.get(i - 1).getTile((int) (x + Vector2f.worldX) / 64 - 1, (int) (y + Vector2f.worldY) / 64 - 1);
                        pos = new Vector2f(x, y);
                        switch(level.get(i - 1).getTile( (int) (Vector2f.worldX + Vector2f.playerX - player.getSize() / 2) / 64, (int) (Vector2f.worldY + Vector2f.playerY - player.getSize() / 2) / 64 - 1)) {
                            case 0:
                                break;
                            case 1:
                                break;
                            case 2:
                                break;
                            case 3:
                                creatingNewLevel();
                                break;
                            case 4:
                                treasure.put(pos, true);
                                loot = r.nextInt(3) + 1;
                                player.addingItem(loot);
                                level.get(i - 1).getTile( (int) (Vector2f.worldX + Vector2f.playerX - player.getSize() / 2) / 64, (int) (Vector2f.worldY + Vector2f.playerY - player.getSize() / 2) / 64 - 1);
                                break;
                            default:
                                break;
                        }
                        for(int i = 0; i < enemies.size(); i++) {
                            if(Vector2f.worldX < enemies.get(i).getPos().x && Vector2f.worldX + gsm.getWidth() > enemies.get(i).getPos().x) {
                                if(Vector2f.worldY < enemies.get(i).getPos().y && Vector2f.worldY + gsm.getHeight() > enemies.get(i).getPos().y) {

                                }
                            }
                        }


                        Sprite.drawTile(graphic, tile, tileType, new Vector2f(x, y), 64, 64, 0, 0);
                    }
                //}
            }
        //}
    }
}