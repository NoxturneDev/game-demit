import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements  Runnable{

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

    final int FPS = 60;

//    SYSTEM
    TileManager tm = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    AssetSetter aSetter = new AssetSetter(this);
    CollisionChecker collisionChecker = new CollisionChecker(this);

//    ENTITIES
    Player player = new Player(this, keyH);

//    UI
    DialogBox dbox = new DialogBox(this);

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
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000.0/ FPS;
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
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        tm.draw(g2);
        player.draw(g2);
        dbox.drawDialogBox(g2);
    }
}
