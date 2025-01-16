package main;

import ai.PathFinder;
import entities.Entity;
import entities.Projectile;
import objects.Items;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
    public int currentMap = 0;
    public int currentLevel = 0;
    final int FPS = 60;

    //    GAME STATE
    public boolean bossBattle = false;
    public Entity currentActiveNPC;

    //    SYSTEM

    public TileManager tm = new TileManager(this);
    //    public CutsceneManager cutsceneManager = new CutsceneManager(this);
    KeyHandler keyH = new KeyHandler(this);
    Thread gameThread;
    Thread configThread;
    AssetSetter aSetter = new AssetSetter(this);
    EventHandler eHandler = new EventHandler(this);
    public Leaderboard leaderboardHandler = new Leaderboard(this);
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public PathFinder pathFinder = new PathFinder(this);
    Sound music = new Sound(); // Created 2 different objects for Sound Effect and Music. If you use 1 object SE or Music stops sometimes.
    public UI ui = new UI(this);
    Config config = new Config(this);
    long interval = 3000; // autosave interval
    public SceneManager sceneManager = new SceneManager(this);
    public GameInputBox gameInputBox = new GameInputBox(this);

    //    ENTITIES
    public Player player = new Player(this, keyH);
    public Entity monsters[][] = new Entity[maxMap][10];
    public Entity npc[][] = new Entity[maxMap][10];
    public Entity projectile[][] = new Entity[maxMap][20]; // cut projectile
    public Items[][] items = new Items[maxMap][10];
    Lighting lighting = new Lighting(this);
    public ArrayList<Entity> particleList = new ArrayList<>();
    ArrayList<Entity> entityList = new ArrayList<>();
    public ArrayList<Projectile> projectileList = new ArrayList<>();

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
    public final int LEADERBOARD_SCREEN = 10;
    public final int LEVEL_SCREEN = 11;
    public final int ENTER_USERNAME = 12;
    public final int PROFILE_SCREEN = 13;
    public final int OVERLAY_TEXT = 14;
    public final int SPLASH_SCREEN = 99;
    public final int OVERLAY_ENDING_TEXT = 100;
    public final int ENDING_SCREEN = 101;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public GamePanel getCurrentStats() {
        return this;
    }

    public void setupGame() {
        aSetter.setItem();
        aSetter.setNPC();
        aSetter.setMonsters();
//        eManager.setup();

//        ui.cutsceneIndex = 1;
        sceneManager.playScene(SceneManager.SceneIndex.SPLASH_SCREEN.ordinal());
//        config.saveConfigToMongoDB();
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

    public void startConfigThread() {
        if (gameState == PLAY) {
            config.interval = interval;
            configThread = new Thread(config);
            configThread.start();
        }
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

        for(int i = 0; i < projectile[1].length; i++)
        {
            if(projectile[currentMap][i] != null)
            {
                if(projectile[currentMap][i].alive == true)
                {
                    projectile[currentMap][i].update();
                }
                if(projectile[currentMap][i].alive == false)
                {
                    projectile[currentMap][i] = null;
                }
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
        entityList.add(player);

        for (int i = 0; i < items[1].length; i++) {
            if (items[currentMap][i] != null) {
                entityList.add(items[currentMap][i]);
            }
        }

//        npc drawing
        for (int i = 0; i < npc[1].length; i++) {
            if (npc[currentMap][i] != null) {
                entityList.add(npc[currentMap][i]);
            }
        }

        for (int i = 0; i < monsters[1].length; i++) {
            if (monsters[currentMap][i] != null) {
                entityList.add(monsters[currentMap][i]);
            }
        }

        for (int i = 0; i < projectile[1].length; i++) {
            if (projectile[currentMap][i] != null) {
                if (projectile[currentMap][i].alive) {
                    entityList.add(projectile[currentMap][i]);
                }
            }
        }

        //SORT
        Collections.sort(entityList, new Comparator<Entity>() {
            @Override
            public int compare(Entity e1, Entity e2) {
                int result = Integer.compare(e1.worldY, e2.worldY);   // result returns : (x=y : 0, x>y : >0, x<y : <0)
                return result;
            }
        });

        //DRAW ENTITIES
        for(int i = 0; i < entityList.size(); i++)
        {
            entityList.get(i).draw(g2);
        }

        //EMPTY ENTITY LIST
        entityList.clear();

//        player drawing
        player.draw(g2);
        lighting.draw(g2);
        ui.draw(g2);
    }
}
