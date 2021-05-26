package TheGaem;

import TheGaem.States.GameStateManager;
import TheGaem.Util.KeyHandler;
import TheGaem.Util.MouseHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements Runnable {
    public static int width;
    public static int height;

    private Thread thread;
    private BufferedImage img;
    private Graphics2D graphic;
    private boolean running = false;

    private MouseHandler mouse;
    private KeyHandler key;
    private Font font;

    private int frameCount = 0;

    private GameStateManager gsm;

    public GamePanel(int width, int height) {
        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        requestFocus();
    }

    public int getFrameCount() { return frameCount; }

    public void addNotify() {
        super.addNotify();
        if(thread == null) {
            thread = new Thread(this, "GamePanel");
            thread.start();
        }
    }

    public void init() {
        running = true;
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        graphic = (Graphics2D) img.getGraphics();
        mouse = new MouseHandler(this);
        key = new KeyHandler(this);

        gsm = new GameStateManager();
    }

    public void run() {
        init();

        final double FRAME_RATE = 10.0; // tu 10.0
        final double TBU = 100000000 / FRAME_RATE; // Time before update

        final int MUBR = 5; // Must update before render - tu wcześniej było 5

        double lastUpdateTime = System.nanoTime();
        double lastRenderTime;

        final double TARGET_FPS = 60.0;
        final double TTBR = 1000000000 / TARGET_FPS; // Total time before render

        int frameCount = 0;
        int lastSecondTime = (int) (lastUpdateTime / 1000000000);
        int oldFrameCount = 0;

        while(running) { // Game loop - timer
            double now = System.nanoTime();
            int updateCount = 0;

            while(((now - lastUpdateTime) > TBU) && (updateCount < MUBR)) {
                update();
                input(mouse, key);
                lastUpdateTime += TBU;
                updateCount++;
            }

            if(now - lastUpdateTime > TBU) {
                lastUpdateTime = now - TBU;
            }

            input(mouse, key);
            render();
            draw();
            lastRenderTime = now;
            frameCount++;

            int thisSecond = (int) (lastUpdateTime / 1000000000);
            if(thisSecond > lastSecondTime) {
                if(frameCount != oldFrameCount) {
                    System.out.println("NEW SECOND " + thisSecond + " " + frameCount);

                    oldFrameCount = frameCount;
                }
                frameCount = 0;
                lastSecondTime = thisSecond;
            }

            while((now - lastRenderTime < TTBR) && (now - lastUpdateTime < TBU)) {
                Thread.yield();

                try{
                    Thread.sleep(2);
                }catch(Exception e) {
                    System.out.println("ERROR: yielding thread");
                }

                now = System.nanoTime();
            }
        }
    }

    public void input(MouseHandler mouse, KeyHandler key) {
        gsm.input(mouse, key);
    }

    public void update() {
        gsm.update();
    }

    public void render() {
        if(graphic != null) {
            graphic.setColor(Color.BLACK);
            graphic.fillRect(0, 0, width, height);
            gsm.render(graphic);
        }
    }

    public void draw() {
        Graphics g2 = (Graphics) this.getGraphics();
        g2.drawImage(img, 0, 0, width, height, null);
        g2.dispose();
    }
}
