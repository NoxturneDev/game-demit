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
    public boolean invincible = false;
    public int invincibleCounter;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;
        HP = 100;

        screenX = gp.screenWidth / 2 - gp.tileSize / 2;
        screenY = gp.screenHeight / 2 - gp.tileSize / 2;

        solidArea = new Rectangle();
        solidArea.x = 6;
        solidArea.y = 14;
        solidArea.width = 32;
        solidArea.height = 32;

        attackArea.width = 32;
        attackArea.height = 32;

        setDefaultValues();
        loadPlayerImage();
        loadPlayerAttackImage();
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

    public void loadPlayerAttackImage() {
        attackUp1 = loadImage("/player/mc_attack_left_1.png", gp.tileSize, gp.tileSize);
        attackUp2 = loadImage("/player/mc_attack_left_2.png", gp.tileSize, gp.tileSize);
        attackDown1 = loadImage("/player/mc_attack_right_1.png", gp.tileSize, gp.tileSize);
        attackDown2 = loadImage("/player/mc_attack_right_2.png", gp.tileSize, gp.tileSize);
        attackRight1 = loadImage("/player/mc_attack_left_1.png", gp.tileSize, gp.tileSize);
        attackRight2 = loadImage("/player/mc_attack_left_2.png", gp.tileSize, gp.tileSize);
        attackLeft1 = loadImage("/player/mc_attack_right_1.png", gp.tileSize, gp.tileSize);
        attackLeft2 = loadImage("/player/mc_attack_right_2.png", gp.tileSize, gp.tileSize);
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 4;
        worldY = gp.tileSize * 2;
        speed = 5;
        direction = "down";
    }

    public void interactNPC(int i) {
        if (i != 999) {
            gp.gameState = gp.DIALOGUE;
            gp.npc[i].speak();
        }
    }

    public void contactMonster(int i) {
        if (i != 999) {
            if (!invincible) {
                HP -= gp.monsters[i].damage;
                invincible = true;
            }
        }
    }

    public void pickupItem(int itemIndex) {
        if (itemIndex != 999) {
            gp.items[itemIndex] = null;
        }
    }

    public void attacking() {
        attacking = true;
        spriteCounter++;

        if (spriteCounter < 5) {
            spriteNum = 1;
        } else if (spriteCounter > 5 && spriteCounter < 25) {
            spriteNum = 2;

            int currWorldX = worldX;
            int currWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            switch (direction) {
                case"up": worldY -= attackArea.height; break;
                case"down": worldY += attackArea.height; break;
                case"right": worldX += attackArea.width; break;
                case"left": worldX -= attackArea.width; break;
            }

            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            int monsterIndex = gp.collisionChecker.checkEntity(this, gp.monsters);
            attackMonster(monsterIndex);

            worldX = currWorldX;
            worldY = currWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;

        } else if (spriteCounter > 25) {
            spriteNum = 2;
            spriteCounter = 0;
            attacking = false;
        }
    }

    public void attackMonster(int i) {
        if (i != 999) {
            gp.monsters[i].HP -= 5;

            if (gp.monsters[i].HP <= 0) {
                gp.monsters[i] = null;
            }
        }
    }

    public void update() {
        if (gp.gameState == gp.PLAY) {
            if (HP <= 0) {
                gp.gameState = gp.DIED;
            }

            if (attacking) {
                attacking();
            } else if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
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

                int monsterIndex = gp.collisionChecker.checkEntity(this, gp.monsters);
                contactMonster(monsterIndex);

//                player to items collision
                int objIndex = gp.collisionChecker.checkObjectCollision(this, true);
                pickupItem(objIndex);


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

            invincibleCounter++;
            if (invincible) {
                if (invincibleCounter > 60) {
                    invincible = false;
                    invincibleCounter = 0;
                }
            }
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage img = null;

        switch (direction) {
            case "up":
                if (!attacking) {
                    if (spriteNum == 1) {
                        img = up1;
                    }
                    if (spriteNum == 2) {
                        img = up2;
                    }
                    if (spriteNum == 3) {
                        img = up3;
                    }
                }
                if (attacking) {
                    if (spriteNum == 1) {
                        img = attackUp1;
                    }
                    if (spriteNum == 2) {
                        img = attackUp2;
                    }
                    if (spriteNum == 3) {
                        img = attackUp1;
                    }
                }
                break;
            case "down":
                if (!attacking) {
                    if (spriteNum == 1) {
                        img = down1;
                    }
                    if (spriteNum == 2) {
                        img = down2;
                    }
                    if (spriteNum == 3) {
                        img = down3;
                    }
                }
                if (attacking) {
                    if (spriteNum == 1) {
                        img = attackDown1;
                    }
                    if (spriteNum == 2) {
                        img = attackDown2;
                    }
                    if (spriteNum == 3) {
                        img = attackDown1;
                    }
                }
                break;
            case "left":
                if (!attacking) {
                    if (spriteNum == 1) {
                        img = left1;
                    }
                    if (spriteNum == 2) {
                        img = left2;
                    }
                    if (spriteNum == 3) {
                        img = left3;
                    }
                }
                if (attacking) {
                    if (spriteNum == 1) {
                        img = attackLeft1;
                    }
                    if (spriteNum == 2) {
                        img = attackLeft2;
                    }
                    if (spriteNum == 3) {
                        img = attackLeft1;
                    }
                }
                break;
            case "right":
                if (!attacking) {
                    if (spriteNum == 1) {
                        img = right1;
                    }
                    if (spriteNum == 2) {
                        img = right2;
                    }
                    if (spriteNum == 3) {
                        img = right3;
                    }
                }
                if (attacking) {
                    if (spriteNum == 1) {
                        img = attackRight1;
                    }
                    if (spriteNum == 2) {
                        img = attackRight2;
                    }
                    if (spriteNum == 3) {
                        img = attackRight1;
                    }
                }
                break;
        }

        g2.drawImage(img, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}
