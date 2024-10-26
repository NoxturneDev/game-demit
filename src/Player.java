import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
        loadPlayerImage();
    }

    public void loadPlayerImage() {
        try {
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player_up_1.png")));
//            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player_up_2.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player_down_1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player_walk_down_2.png")));
            down3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player_walk_down_3.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player_left_1.png")));
//            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player_left_2.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player_right_1.png")));
//            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player_right_2.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 4;
        direction = "down";
    }

    public void update() {
        if (keyH.upPressed) {
            direction = "up";
            y -= speed;
        } else if (keyH.downPressed) {
            direction = "down";
            y += speed;
        } else if (keyH.leftPressed) {
            direction = "left";
            x -= speed;
        } else if (keyH.rightPressed) {
            direction = "right";
            x += speed;
        }

        spriteCounter++;
        if (spriteCounter > 7) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 3;
            } else if (spriteNum == 3) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage img = null;

        switch (direction) {
            case "up":
                img = up1;
                break;
            case "down":
                if (spriteNum == 1) {
                    img = down1;
                } else if (spriteNum == 2) {
                    img = down2;
                } else if (spriteNum == 3) {
                    img = down3;
                }
                break;
            case "left":
                img = left1;
                break;
            case "right":
                img = right1;
                break;
        }

        g2.drawImage(img, x, y, gp.tileSize, gp.tileSize, null);
    }
}
