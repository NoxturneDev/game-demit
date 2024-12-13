package main;

import entities.Entity;
import objects.Items;

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
    public int maxWorldCol = 50;
    public int maxWorldRow = 50;
    public int maxMap = 99;
    public int currentMap = 5;


    final int FPS = 60;

    //    SYSTEM
    TileManager tm = new TileManager(this);
    KeyHandler keyH = new KeyHandler(this);
    Thread gameThread;
    Thread configThread;
    AssetSetter aSetter = new AssetSetter(this);
    EventHandler eHandler = new EventHandler(this);
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    Sound music = new Sound(); // Created 2 different objects for Sound Effect and Music. If you use 1 object SE or Music stops sometimes.
    public UI ui = new UI(this);
    Config config = new Config(this);
    public SceneManager sceneManager = new SceneManager(this);


    //    ENTITIES
    public Player player = new Player(this, keyH);
    public Entity monsters[][] = new Entity[maxMap][10];
    public Entity npc[][] = new Entity[maxMap][10];
    public Items[][] items = new Items[maxMap][10];
    Lighting lighting = new Lighting(this);

    //    Game state
    public int gameState;
    public final int TITLE = 0;
    public final int PLAY = 1;
    public final int PAUSED = 2;
    public final int DIALOGUE = 3;
    public final int DIED = 4;
    public final int ITEM_DROP = 5;
    public final int JUMPSCARE_SCREEN = 6;
    public final int CUTSCENE = 7;
    public final int QUIZ = 8;
    public final int RUNNING_TEXT = 9;

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
        aSetter.setMonsters();
//        eManager.setup();

//        ui.cutsceneIndex = 1;
        sceneManager.playScene(1);
        config.saveConfigToMongoDB();
//        playMusic(0);
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void playSE(int i) {
        music.setFile(i);
        music.play();
    }

    public void stopMusic() {
        music.stop();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void startConfigThread(long interval) {
        config.interval = interval;
        configThread = new Thread(config);
        configThread.start();
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

        for (int i = 0; i < npc[1].length; i++) {
            if (npc[currentMap][i] != null) {
                npc[currentMap][i].update();
            }
        }

        for (int i = 0; i < monsters[1].length; i++) {
            if (monsters[currentMap][i] != null) {
                monsters[currentMap][i].update();
            }
        }

//        if (gameState == CUTSCENE || gameState == JUMPSCARE_SCREEN) {
        ui.update();
//        }
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

//        tile drawing
        tm.draw(g2);

//        item drawing
        for (int i = 0; i < items[1].length; i++) {
            if (items[currentMap][i] != null) {
                items[currentMap][i].draw(g2);
            }
        }

//        npc drawing
        for (int i = 0; i < npc[1].length; i++) {
            if (npc[currentMap][i] != null) {
                npc[currentMap][i].draw(g2);
            }
        }

        for (int i = 0; i < monsters[1].length; i++) {
            if (monsters[currentMap][i] != null) {
                monsters[currentMap][i].draw(g2);
            }
        }

//        player drawing
        player.draw(g2);
        lighting.draw(g2);
        ui.draw(g2);
    }
}
