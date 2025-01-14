package entities;

import main.GamePanel;

import java.awt.*;
import java.util.Random;

public class MON_Butoijo extends Entity {
    public MON_Butoijo(GamePanel gp) {
        super(gp);
        name = "Butoijo";
        direction = "down";
        speed = 2;
        damage = new Random().nextInt(20) + 5;
        boss = true;
        life = 100;
        maxLife = 100;

        int size = gp.tileSize * 2;
        solidArea.x = 48;
        solidArea.y = 48;
        solidArea.width = size - 48*2;
        solidArea.height = size - 48 ;

        defaultSolidAreaX = solidArea.x;
        defaultSolidAreaY = solidArea.y;
        projectile = new OBJ_Fireball(gp);
        getImage();
//        setDialogue();
    }

    public void getImage() {
        int scale = 2;
        up1 = loadImage("/monster/butoijo/butoijo_idle_1.png", gp.tileSize * scale, gp.tileSize * scale);
        up2 = loadImage("/monster/butoijo/butoijo_idle_2.png", gp.tileSize * scale, gp.tileSize * scale);
        up3 = loadImage("/monster/butoijo/butoijo_idle_3.png", gp.tileSize * scale, gp.tileSize * scale);
        up4 = loadImage("/monster/butoijo/butoijo_idle_4.png", gp.tileSize * scale, gp.tileSize * scale);
        down1 = loadImage("/monster/butoijo/butoijo_idle_1.png", gp.tileSize * scale, gp.tileSize * scale);
        down2 = loadImage("/monster/butoijo/butoijo_idle_2.png", gp.tileSize * scale, gp.tileSize * scale);
        down3 = loadImage("/monster/butoijo/butoijo_idle_3.png", gp.tileSize * scale, gp.tileSize * scale);
        down4 = loadImage("/monster/butoijo/butoijo_idle_4.png", gp.tileSize * scale, gp.tileSize * scale);
        left1 = loadImage("/monster/butoijo/butoijo_idle_1.png", gp.tileSize * scale, gp.tileSize * scale);
        left2 = loadImage("/monster/butoijo/butoijo_idle_2.png", gp.tileSize * scale, gp.tileSize * scale);
        left3 = loadImage("/monster/butoijo/butoijo_idle_3.png", gp.tileSize * scale, gp.tileSize * scale);
        left4 = loadImage("/monster/butoijo/butoijo_idle_4.png", gp.tileSize * scale, gp.tileSize * scale);
        right1 = loadImage("/monster/butoijo/butoijo_idle_1.png", gp.tileSize * scale, gp.tileSize * scale);
        right2 = loadImage("/monster/butoijo/butoijo_idle_2.png", gp.tileSize * scale, gp.tileSize * scale);
        right3 = loadImage("/monster/butoijo/butoijo_idle_3.png", gp.tileSize * scale, gp.tileSize * scale);
        right4 = loadImage("/monster/butoijo/butoijo_idle_4.png", gp.tileSize * scale, gp.tileSize * scale);
    }


    public void setAction() {
//        if(onPath == true)
//        {
//            int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
//            int goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;
//        }
//        else
//        {
        if (getTileDistance(gp.player)< 50) {
            moveTowardPlayer(60);
            checkShootOrNot(10, 30);
        } else  {
            getRandomDirection(120);
        }
//
//        if(onPath == true)
//        {
//
//            //Check if it stops chasing
//            checkStopChasingOrNot(gp.player,5,100);
//
//            //Search the direction to go
//            searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
//
//            //Check if it shoots a projectile
//            checkShootOrNot(200, 30); //Just added to red slimes
//        }
//        else
//        {
//            //Check if it starts chasing
//            checkStartChasingOrNot(gp.player, 5, 100);
//
//            //Get a random direction
//            getRandomDirection(120);
//        }
//        int i = new Random().nextInt(60)*1;
//        if (i < 50) {
//            checkShootOrNot(10, 5);
//        }
//        actionLockCounter++;
//
//        if (actionLockCounter == 120) {
//            Random random = new Random();
//            int i = random.nextInt(100) + 1;  // pick up  a number from 1 to 100
//            if (i <= 25) {
//                direction = "up";
//            }
//            if (i > 25 && i <= 50) {
//                direction = "down";
//            }
//            if (i > 50 && i <= 75) {
//                direction = "left";
//            }
//            if (i > 75 && i <= 100) {
//                direction = "right";
//            }
//            actionLockCounter = 0; // reset
//        }
//        }
    }
}
