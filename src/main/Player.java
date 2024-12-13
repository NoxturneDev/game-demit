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
    public int totalScore;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;
        HP = 100;

//        render character in the middle of the screen view
        screenX = gp.screenWidth / 2 - gp.tileSize / 2;
        screenY = gp.screenHeight / 2 - gp.tileSize / 2;

        solidArea = new Rectangle();
        solidArea.x = 14;
        solidArea.y = 24;
        solidArea.width = 34;
        solidArea.height = 40;
        defaultSolidAreaX = 14;
        defaultSolidAreaY = 24;

        attackArea.width = 64;
        attackArea.height = 64;

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
        attackUp1 = loadImage("/player/mc_attack_left_1.png", gp.tileSize * 2, gp.tileSize );
        attackUp2 = loadImage("/player/mc_attack_left_2.png", gp.tileSize * 2, gp.tileSize);
        attackUp3 = loadImage("/player/mc_attack_left_3.png", gp.tileSize * 2, gp.tileSize);
        attackDown1 = loadImage("/player/mc_attack_right_1.png", gp.tileSize * 2, gp.tileSize);
        attackDown2 = loadImage("/player/mc_attack_right_2.png", gp.tileSize * 2, gp.tileSize);
        attackDown3 = loadImage("/player/mc_attack_right_3.png", gp.tileSize * 2, gp.tileSize);
        attackRight1 = loadImage("/player/mc_attack_right_1.png", gp.tileSize * 2, gp.tileSize);
        attackRight2 = loadImage("/player/mc_attack_right_2.png", gp.tileSize * 2, gp.tileSize);
        attackRight3 = loadImage("/player/mc_attack_right_3.png", gp.tileSize * 2, gp.tileSize);
        attackLeft1 = loadImage("/player/mc_attack_left_1.png", gp.tileSize * 2, gp.tileSize);
        attackLeft2 = loadImage("/player/mc_attack_left_2.png", gp.tileSize * 2, gp.tileSize);
        attackLeft3 = loadImage("/player/mc_attack_left_3.png", gp.tileSize * 2, gp.tileSize);
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 16;
        speed = 5;
        direction = "down";
    }

    public void interactNPC(int i) {
        if (i != 999) {
            gp.gameState = gp.DIALOGUE;
            gp.npc[gp.currentMap][i].speak();
        }
    }

    public void contactMonster(int i) {
        if (i != 999) {
            if (!invincible) {
                gp.playSE(1);
                HP -= gp.monsters[gp.currentMap][i].damage;
                invincible = true;
            }
        }
    }

    public void pickupItem(int itemIndex) {
        if (itemIndex != 999) {
//            set UI attributes for item drop screen
            gp.ui.itemName = gp.items[gp.currentMap][itemIndex].itemName;
            gp.ui.itemDescription = gp.items[gp.currentMap][itemIndex].itemDescription;
            gp.ui.itemIcon = gp.items[gp.currentMap][itemIndex].itemIcon;
            gp.gameState = gp.ITEM_DROP;

            gp.items[itemIndex] = null;
            gp.player.speed = 10;
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

            int monsterIndex = gp.collisionChecker.checkEntity(this, gp.monsters[gp.currentMap]);
            attackMonster(monsterIndex);

            worldX = currWorldX;
            worldY = currWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;

        } else if (spriteCounter > 25) {
            spriteNum = 3;
            spriteCounter = 0;
            attacking = false;
        }
    }

    public void attackMonster(int i) {
        if (i != 999) {
            gp.monsters[gp.currentMap][i].HP -= 5;

            if (gp.monsters[gp.currentMap][i].HP <= 0) {
                gp.monsters[gp.currentMap][i] = null;
                gp.aSetter.totalMonsterMap7--;
                totalScore += 10;

                if (gp.aSetter.totalMonsterMap7 < 0) {
                    gp.sceneManager.playScene(10);
                }
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
                int npxIndex = gp.collisionChecker.checkEntity(this, gp.npc[gp.currentMap]);
                interactNPC(npxIndex);

                int monsterIndex = gp.collisionChecker.checkEntity(this, gp.monsters[gp.currentMap]);
                contactMonster(monsterIndex);

//                player to items collision
                int objIndex = gp.collisionChecker.checkObjectCollision(this, true);
                pickupItem(objIndex);

//                check event
                gp.eHandler.checkEvent();


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
        int tempScreenX = screenX;
        int tempScreenY = screenY;

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
                    tempScreenY = screenY - gp.tileSize;
                    if (spriteNum == 1) {
                        img = attackUp1;
                    }
                    if (spriteNum == 2) {
                        img = attackUp2;
                    }
                    if (spriteNum == 3) {
                        img = attackUp3;
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
                        img = attackDown3;
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
                    tempScreenX = screenX - gp.tileSize;
                    if (spriteNum == 1) {
                        img = attackLeft1;
                    }
                    if (spriteNum == 2) {
                        img = attackLeft2;
                    }
                    if (spriteNum == 3) {
                        img = attackLeft3;
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
                        img = attackRight3;
                    }
                }
                break;
        }

        g2.drawImage(img, tempScreenX, tempScreenY, null);
//        DEBUG
        g2.setColor(Color.RED);
        g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
    }
}
