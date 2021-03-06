
package sega;

import audio.AudioPlayer;
import framework.KeyInput;
import framework.ObjectId;
import framework.Texture;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import static sega.Const.Background.*;
import static sega.Const.Game.*;


public class Game extends Canvas implements Runnable {
    private boolean running = false;
    private Thread thread;

    public static int WIDTH;

    private BufferedImage level0;
    private BufferedImage bgStreet;
    private BufferedImage matan;
    private BufferedImage prg;
    private BufferedImage fizika;
    private BufferedImage eng;
    private BufferedImage telek;

    private Hud hud;

    private Handler handler;
    private Camera cam;
    private static Texture tex;

    private Menu menu;

    private AudioPlayer bgMusic;

    public enum STATE {
        MENU,
        GAME
    }

    public static STATE State = STATE.MENU;

    public static int LEVEL = 0;

    public synchronized void start() {
        if (running) return;

        running = true;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        init();
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                updates++;
                delta--;
            }
            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames + " TICKS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }

    private void init() {
        WIDTH = getWidth();

        tex = new Texture();

        loadImages();

        cam = new Camera(0, 0);
        handler = new Handler(cam);

        handler.loadImageLevel(level0);

        hud = new Hud();

        menu = new Menu();

        bgMusic = new AudioPlayer(BG_MUSIC);
        bgMusic.play();

        this.addKeyListener(new KeyInput(handler));
        setFocusable(true);
    }

    private void loadImages() {
        BufferedImageLoader loader = new BufferedImageLoader();
        try {
            level0 = loader.loadImage(LEVEL0);
            bgStreet = loader.loadImage(STREET);
            matan = loader.loadImage(MATAN);
            prg = loader.loadImage(PRG);
            fizika = loader.loadImage(PHYSICS);
            eng = loader.loadImage(ENG);
            telek = loader.loadImage(TV);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void tick() {
        if (State == STATE.GAME) {
            handler.tick();
        }
        for (int i = 0; i < handler.object.size(); i++) {
            if ((handler.object.get(i).getId() == ObjectId.PLAYER) || (handler.object.get(i).getId() == ObjectId.PLAYER_GUN)) {
                cam.tick(handler.object.get(i));
            }
        }
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;

        g.fillRect(0, 0, getWidth(), getHeight());

        if (State == STATE.GAME) {

            g2d.translate(cam.getX(), cam.getY());

            g.drawImage(bgStreet, 0, 0, this);
            drawLevels(g);

            handler.render(g);

            g2d.translate(-cam.getX(), -cam.getY());

            g.drawImage(telek, 0, 0, this);

            hud.render(g);

        } else if (State == STATE.MENU) {
            menu.render(g);
        }
        g.dispose();
        bs.show();
    }

    private void drawLevels(Graphics g) {
        if (LEVEL == 1) g.drawImage(matan, 0, 0, this);
        if (LEVEL == 2) g.drawImage(prg, 0, 0, this);
        if (LEVEL == 3) g.drawImage(fizika, 0, 0, this);
        if (LEVEL == 4) g.drawImage(eng, 0, 0, this);
        if (LEVEL == 5) g.drawImage(bgStreet, 0, 0, this);
    }

    public static Texture getInstance() {
        return tex;
    }

    public static void main(String[] args) {
        new Window(GAME_WEIGHT, GAME_HEIGHT, GAME_NAME, new Game());
    }
}
