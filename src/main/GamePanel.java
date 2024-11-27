package main;

import entities.Entity;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    final int originalTileSize = 32;
    int scale = 2;

    //    TILE SETTING
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenHeight = tileSize * maxScreenRow;
    public final int screenWidth = tileSize * maxScreenCol;

    //    WORLD SETTING
    public final int maxWorldCol = 20;
    public final int maxWorldRow = 20;
    public int maxMap = 99;

    final int FPS = 60;

    //    SYSTEM
    TileManager tm = new TileManager(this);
    KeyHandler keyH = new KeyHandler(this);
    Thread gameThread;
    AssetSetter aSetter = new AssetSetter(this);
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    Sound music = new Sound(); // Created 2 different objects for Sound Effect and Music. If you use 1 object SE or Music stops sometimes.
    public UI ui = new UI(this);

    //    ENTITIES
    public Player player = new Player(this, keyH);
    Entity npc[] = new Entity[10];
    Object item[] = new Object[10];

    //    Main.UI
//    DialogBox dbox = new DialogBox(this);

    //    Game state
    public int gameState;
    public final int TITLE = 0;
    public final int PLAY = 1;
    public final int PAUSED = 2;
    public final int DIALOGUE = 3;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {
        aSetter.setItem();
        aSetter.setNPC();

        gameState = PLAY;
//        playMusic(0);
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000.0 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += currentTime - lastTime;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        player.update();

        for (int i = 0; i < npc.length; i++) {
            if (npc[i] != null) {
                npc[i].update();
            }
        }
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

//        tile drawing
        tm.draw(g2);

//        npc drawing
        for (int i = 0; i < npc.length; i++) {
            if (npc[i] != null) {
                npc[i].draw(g2);
            }
        }

//        player drawing
        player.draw(g2);
        ui.draw(g2);
    }
}
