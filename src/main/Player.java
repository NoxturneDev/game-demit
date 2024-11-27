package main;

import entities.Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - gp.tileSize / 2;
        screenY = gp.screenHeight / 2 - gp.tileSize / 2;

        solidArea = new Rectangle();
        solidArea.x = 6;
        solidArea.y = 14;
        solidArea.width = 32;
        solidArea.height = 32;

        setDefaultValues();
        loadPlayerImage();
    }

    public void loadPlayerImage() {
        up1 = loadImage("/player/mc_walk_down_1.png", gp.tileSize, gp.tileSize);
        up2 = loadImage("/player/mc_walk_down_2.png", gp.tileSize, gp.tileSize);
        up3 = loadImage("/player/mc_walk_down_3.png", gp.tileSize, gp.tileSize);
        down1 = loadImage("/player/mc_walk_down_1.png", gp.tileSize, gp.tileSize);
        down2 = loadImage("/player/mc_walk_down_2.png", gp.tileSize, gp.tileSize);
        down3 = loadImage("/player/mc_walk_down_3.png", gp.tileSize, gp.tileSize);
        left1 = loadImage("/player/mc_walk_right_1.png", gp.tileSize, gp.tileSize);
        left2 = loadImage("/player/mc_walk_right_2.png", gp.tileSize, gp.tileSize);
        left3 = loadImage("/player/mc_walk_right_3.png", gp.tileSize, gp.tileSize);
        right1 = loadImage("/player/mc_walk_left_1.png", gp.tileSize, gp.tileSize);
        right2 = loadImage("/player/mc_walk_left_2.png", gp.tileSize, gp.tileSize);
        right3 = loadImage("/player/mc_walk_left_3.png", gp.tileSize, gp.tileSize);
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 4;
        worldY = gp.tileSize * 2;
        speed = 5;
        direction = "down";
    }

    public void interactNPC(int i) {
        if(i != 999) {
            gp.gameState = gp.DIALOGUE;
            gp.ui.currentNPC = gp.npc[i];
        }
    }

    public void update() {
        if (gp.gameState == gp.PLAY) {
            if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
                if (keyH.upPressed) {
                    direction = "up";
                } else if (keyH.downPressed) {
                    direction = "down";
                } else if (keyH.leftPressed) {
                    direction = "left";
                } else if (keyH.rightPressed) {
                    direction = "right";
                }

                collision = false;
                gp.collisionChecker.checkTileCollision(this);

//                player to npc collission
                int npxIndex = gp.collisionChecker.checkEntity(this, gp.npc);
                interactNPC(npxIndex);

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
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage img = null;

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
