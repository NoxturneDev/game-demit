package entities;

import main.GamePanel;
import main.Utility;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Entity {
    public GamePanel gp;
    //    entity attributes
    public int worldX;
    public int worldY;
    public int speed;
    public String[][] dialogues = new String[20][20];
    public int dialogueSet;
    public int dialogueIndex = 0;
    public int damage;
    public int HP;

    //    entitty assets attributes
    public BufferedImage up1, up2, up3, up4, down1, down2, down3, down4, left1, left2, left3, left4, right1, right2, right3, right4;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle solidArea = new Rectangle(0,0, 48, 48); // default value
    public Rectangle attackArea = new Rectangle(0,0, 0, 0); // default value
    public int defaultSolidAreaX, defaultSolidAreaY;
    public boolean collision = false;

    //    entity state
    public String direction;
    public int actionLockCounter;
    public boolean attacking = false;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public BufferedImage loadImage(String imagePath, int width, int height) {
        Utility util = new Utility();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath));
            image = util.scaleImage(image, width, height);   //it scales to tilesize , will fix for player attack(16px x 32px) by adding width and height
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    public void setAction() {
    }

    public void speak() {
    }

    public void startDialogue(Entity entity, int dialogueSet) {
        gp.gameState = gp.DIALOGUE;
        gp.ui.currentNPC = entity;
        this.dialogueSet = dialogueSet;
    }

    public void update() {
        if (gp.gameState == gp.PLAY) {
            setAction();

            collision = false;
            gp.collisionChecker.checkTileCollision(this);

            if (!collision) {
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage img = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
                && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
                && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
                && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY
        ) {
            switch (direction) {
                case "up":
                    if (spriteNum == 1) {
                        img = up1;
                    } else if (spriteNum == 2) {
                        img = up2;
                    } else if (spriteNum == 3) {
                        img = up3;
                    }
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
                    if (spriteNum == 1) {
                        img = left1;
                    } else if (spriteNum == 2) {
                        img = left2;
                    } else if (spriteNum == 3) {
                        img = left3;
                    }
                    break;
                case "right":
                    if (spriteNum == 1) {
                        img = right1;
                    } else if (spriteNum == 2) {
                        img = right2;
                    } else if (spriteNum == 3) {
                        img = right3;
                    }
                    break;
            }
            g2.drawImage(img, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }

}
