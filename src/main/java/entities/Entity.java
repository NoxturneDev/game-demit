package entities;

import main.GamePanel;
import main.Utility;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class Entity {
    public GamePanel gp;
    //    entity attributes
    public String name;
    public int worldX;
    public int worldY;
    public int speed;
    public int life;
    public int maxLife;
    public int attack;
    public boolean alive;
    public int shotAvailableCounter = 0;
    public String[][] dialogues = new String[20][20];
    public int dialogueSet;
    public int dialogueIndex = 0;
    public String quizQuestion = "";
    public boolean hasQuiz = false;
    public int quizCorrectAnswer = 0;
    public Projectile projectile;

    public int damage;
    public int HP;

    public boolean onPath = false;

    //    entitty assets attributes
    public BufferedImage up1, up2, up3, up4, down1, down2, down3, down4, left1, left2, left3, left4, right1, right2, right3, right4;
    public BufferedImage attackUp1, attackUp2, attackUp3, attackDown1, attackDown2, attackDown3, attackLeft1, attackLeft2, attackLeft3, attackRight1, attackRight2, attackRight3;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48); // default value
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0); // default value
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

    public int getScreenX() {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        return screenX;
    }

    public int getScreenY() {
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        return screenY;
    }

    public int getLeftX() {
        return worldX + solidArea.x;
    }

    public int getRightX() {
        return worldX + solidArea.width + solidArea.width;
    }

    public int getTopY() {
        return worldY + solidArea.y;
    }

    public int getBottomY() {
        return worldY + solidArea.y + solidArea.height;
    }

    public int getCol() {
        return (worldX + solidArea.x) / gp.tileSize;
    }

    public int getRow() {
        return (worldY + solidArea.y) / gp.tileSize;
    }

    public int getCenterX() {
        int centerX = worldX + left1.getWidth() / 2;
        return centerX;
    }

    public int getCenterY() {
        int centerY = worldY + up1.getWidth() / 2;
        return centerY;
    }

    public int getXdistance(Entity target) {
        int xDistance = Math.abs(getCenterX() - target.getCenterX());
        return xDistance;
    }

    public int getYdistance(Entity target) {
        int yDistance = Math.abs(getCenterY() - target.getCenterY());
        return yDistance;
    }

    public int getTileDistance(Entity target) {
        int tileDistance = (getXdistance(target) + getYdistance(target)) / gp.tileSize;
        return tileDistance;
    }

    public void checkAttackOrNot(int rate, int straight, int horizontal) {
        boolean tartgetInRange = false;
        int xDis = getXdistance(gp.player);
        int yDis = getYdistance(gp.player);

        switch (direction) {
            case "up":
                if (gp.player.getCenterY() < getCenterY() && yDis < straight && xDis < horizontal) {
                    tartgetInRange = true;
                }
                break;
            case "down":
                if (gp.player.getCenterY() > getCenterY() && yDis < straight && xDis < horizontal) {
                    tartgetInRange = true;
                }
                break;
            case "left":
                if (gp.player.getCenterX() < getCenterX() && xDis < straight && yDis < horizontal) {
                    tartgetInRange = true;
                }
                break;
            case "right":
                if (gp.player.getCenterX() > getCenterX() && xDis < straight && yDis < horizontal) {
                    tartgetInRange = true;
                }
                break;
        }

        if (tartgetInRange == true) {
            //Check if it initiates an attack
            int i = new Random().nextInt(rate);
            if (i == 0) {
                attacking = true;
                spriteNum = 1;
                spriteCounter = 0;
                shotAvailableCounter = 0;

            }
        }

    }

    public void checkShootOrNot(int rate, int shotInterval) {
        int i = new Random().nextInt(rate);
        if (i == 0 && projectile.alive == false && shotAvailableCounter == shotInterval) {
            projectile.set(getCenterX(), getCenterY(), direction, true, this);
            gp.projectileList.add(projectile);
//            gp.projectile[gp.currentMap][i] = projectile;
            //CHECK VACANCY
            for (int ii = 0; ii < gp.projectile[1].length; ii++) {
                if (gp.projectile[gp.currentMap][ii] == null) {
                    gp.projectile[gp.currentMap][ii] = projectile;
                    break;
                }
            }
            shotAvailableCounter = 0;
        }
    }

    public void damagePlayer(int attack) {
        if (gp.player.invincible == false) {
//            int damage = attack - gp.player.defense;
            int damage = attack;
            //Get an opposite direction of this attacker
            String canGuardDirection = getOppositeDirection(direction);

//            if(gp.player.guarding == true && gp.player.direction.equals(canGuardDirection))
//            {
//                //Parry //If you press guard key less then 10 frames before the attack you receive 0 damage, and you get critical chance
//                if(gp.player.guardCounter < 10)
//                {
//                    damage = 0;
//                    gp.playSE(16);
//                    setKnockBack(this, gp.player, knockBackPower); //Knockback attacker //You can use shield's knockBackPower!
//                    offBalance = true;
//                    spriteCounter =- 60; //Attacker's sprites returns to motion1//like a stun effect
//                }
//                else
//                {
//                    //Normal Guard
//                    damage /= 2;
//                    gp.playSE(15);
//                }
//            }
//            else
//            {
            //Not guarding
//            gp.playSE(6);   //receivedamage.wav
            if (damage < 1) {
                damage = 1;
            }

            if (damage != 0) {
                gp.player.transparent = true;
//                setKnockBack(gp.player, this, knockBackPower);
            }

            //We can give damage
            gp.player.life -= damage;
            gp.player.invincible = true;
            System.out.println("Attacked");
            System.out.println("Player life: " + gp.player.life);
        }
    }

    public void checkStartChasingOrNot(Entity target, int distance, int rate) {
        if (getTileDistance(target) < distance) {
            int i = new Random().nextInt(rate);
            if (i == 0) {
                onPath = true;
            }
        }
    }

    public void checkStopChasingOrNot(Entity target, int distance, int rate) {
        if (getTileDistance(target) > distance) {
            int i = new Random().nextInt(rate);
            if (i == 0) {
                onPath = false;
            }
        }
    }

    public void getRandomDirection(int interval) {
        actionLockCounter++;

        if (actionLockCounter > interval) {
            Random random = new Random();
            int i = random.nextInt(100) + 1;  // pick up  a number from 1 to 100
            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "left";
            }
            if (i > 75 && i <= 100) {
                direction = "right";
            }
            actionLockCounter = 0; // reset
        }
    }

    public int getGoalCol(Entity target) {
        int goalCol = (target.worldX + target.solidArea.x) / gp.tileSize;
        return goalCol;

    }

    public int getGoalRow(Entity target) {
        int goalRow = (target.worldY + target.solidArea.y) / gp.tileSize;
        return goalRow;
    }

    public void moveTowardPlayer(int interval) {
        actionLockCounter++;

        if (actionLockCounter > interval) {
            if (getXdistance(gp.player) > getYdistance(gp.player)) //if entity far to the player on X axis moves right or left
            {
                if (gp.player.getCenterX() < getCenterX()) //Player is left side, entity moves to left
                {
                    direction = "left";
                } else {
                    direction = "right";
                }
            } else if (getXdistance(gp.player) < getYdistance(gp.player))  //if entity far to the player on Y axis moves up or down
            {
                if (gp.player.getCenterY() < getCenterY()) //Player is up side, entity moves to up
                {
                    direction = "up";
                } else {
                    direction = "down";
                }
            }
            actionLockCounter = 0;
        }
    }

    public String getOppositeDirection(String direction) {
        String oppositeDirection = "";

        switch (direction) {
            case "up":
                oppositeDirection = "down";
                break;
            case "down":
                oppositeDirection = "up";
                break;
            case "left":
                oppositeDirection = "right";
                break;
            case "right":
                oppositeDirection = "left";
                break;
        }

        return oppositeDirection;
    }

    public void searchPath(int goalCol, int goalRow) {
        int startCol = (worldX + solidArea.x) / gp.tileSize;
        int startRow = (worldY + solidArea.y) / gp.tileSize;
        gp.pathFinder.setNodes(startCol, startRow, goalCol, goalRow, this);
        if (gp.pathFinder.search() == true) {
            //Next WorldX and WorldY
            int nextX = gp.pathFinder.pathList.get(0).col * gp.tileSize;
            int nextY = gp.pathFinder.pathList.get(0).row * gp.tileSize;

            //Entity's solidArea position
            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBottomY = worldY + solidArea.y + solidArea.height;

            // TOP PATH
            if (enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                direction = "up";
            }
            // BOTTOM PATH
            else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                direction = "down";
            }
            // RIGHT - LEFT PATH
            else if (enTopY >= nextY && enBottomY < nextY + gp.tileSize) {
                //either left or right
                // LEFT PATH
                if (enLeftX > nextX) {
                    direction = "left";
                }
                // RIGHT PATH
                if (enLeftX < nextX) {
                    direction = "right";
                }
            }
            //OTHER EXCEPTIONS
            else if (enTopY > nextY && enLeftX > nextX) {
                // up or left
                direction = "up";
                checkCollision();
                if (collision == true) {
                    direction = "left";
                }
            } else if (enTopY > nextY && enLeftX < nextX) {
                // up or right
                direction = "up";
                checkCollision();
                if (collision == true) {
                    direction = "right";
                }
            } else if (enTopY < nextY && enLeftX > nextX) {
                // down or left
                direction = "down";
                checkCollision();
                if (collision == true) {
                    direction = "left";
                }
            } else if (enTopY < nextY && enLeftX < nextX) {
                // down or right
                direction = "down";
                checkCollision();
                if (collision == true) {
                    direction = "right";
                }
            }
            // for following player, disable this. It should be enabled when npc walking to specified location
//            int nextCol = gp.pathFinder.pathList.get(0).col;
//            int nextRow = gp.pathFinder.pathList.get(0).row;
//            if(nextCol == goalCol && nextRow == goalRow)
//            {
//                onPath = false;
//            }
        }
    }

    public int getDetected(Entity user, Entity target[][], String targetName) {
        int index = 999;

        //Check the surrounding object
        int nextWorldX = user.getLeftX();
        int nextWorldY = user.getTopY();

        switch (user.direction) {
            case "up":
                nextWorldY = user.getTopY() - gp.player.speed;
                break;
            case "down":
                nextWorldY = user.getBottomY() + gp.player.speed;
                break;
            case "left":
                nextWorldX = user.getLeftX() - gp.player.speed;
                break;
            case "right":
                nextWorldX = user.getRightX() + gp.player.speed;
                break;
        }
        int col = nextWorldX / gp.tileSize;
        int row = nextWorldY / gp.tileSize;

        for (int i = 0; i < target[1].length; i++) {
            if (target[gp.currentMap][i] != null) {
                if (target[gp.currentMap][i].getCol() == col                                //checking if player 1 tile away from target (key etc.) (must be same direction)
                        && target[gp.currentMap][i].getRow() == row
                        && target[gp.currentMap][i].name.equals(targetName)) {
                    index = i;
                    break;
                }
            }

        }
        return index;
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

    public void checkCollision() {
        collision = false;
        gp.collisionChecker.checkTileCollision(this);
    }

    public void update() {
        if (gp.gameState == gp.PLAY) {
            setAction();

            checkCollision();
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

            if(shotAvailableCounter < 30)
            {
                shotAvailableCounter++;
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
                    } else if (spriteNum == 4) {
                        img = up4;
                    }
                    break;
                case "down":
                    if (spriteNum == 1) {
                        img = down1;
                    } else if (spriteNum == 2) {
                        img = down2;
                    } else if (spriteNum == 3) {
                        img = down3;
                    } else if (spriteNum == 4) {
                        img = down4;
                    }
                    break;
                case "left":
                    if (spriteNum == 1) {
                        img = left1;
                    } else if (spriteNum == 2) {
                        img = left2;
                    } else if (spriteNum == 3) {
                        img = left3;
                    } else if (spriteNum == 4) {
                        img = left4;
                    }
                    break;
                case "right":
                    if (spriteNum == 1) {
                        img = right1;
                    } else if (spriteNum == 2) {
                        img = right2;
                    } else if (spriteNum == 3) {
                        img = right3;
                    } else if (spriteNum == 4) {
                        img = right4;
                    }
                    break;
            }
            g2.drawImage(img, screenX, screenY, null);
////            DEBUG
            g2.setColor(Color.RED);
            g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, img.getWidth(), img.getHeight());
        }
    }

}
