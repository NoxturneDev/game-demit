package entities;

import main.GamePanel;

import java.awt.*;
import java.util.Random;

public class MON_Butoijo extends Entity {
    public MON_Butoijo(GamePanel gp) {
        super(gp);
        direction = "down";
        speed = 2;
        damage = new Random().nextInt(20) + 5;
        HP = 20;

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;

        defaultSolidAreaX = 8;
        defaultSolidAreaY = 16;
        getImage();
//        setDialogue();
    }

    public void getImage() {
        up1 = loadImage("/monster/butoijo/butoijo_idle_1.png", gp.tileSize, gp.tileSize);
        up2 = loadImage("/monster/butoijo/butoijo_idle_2.png", gp.tileSize, gp.tileSize);
        up3 = loadImage("/monster/butoijo/butoijo_idle_3.png", gp.tileSize, gp.tileSize);
        up4 = loadImage("/monster/butoijo/butoijo_idle_4.png", gp.tileSize, gp.tileSize);
        down1 = loadImage("/monster/butoijo/butoijo_idle_1.png", gp.tileSize, gp.tileSize);
        down2 = loadImage("/monster/butoijo/butoijo_idle_2.png", gp.tileSize, gp.tileSize);
        down3 = loadImage("/monster/butoijo/butoijo_idle_3.png", gp.tileSize, gp.tileSize);
        down4 = loadImage("/monster/butoijo/butoijo_idle_4.png", gp.tileSize, gp.tileSize);
        left1 = loadImage("/monster/butoijo/butoijo_idle_1.png", gp.tileSize, gp.tileSize);
        left2 = loadImage("/monster/butoijo/butoijo_idle_2.png", gp.tileSize, gp.tileSize);
        left3 = loadImage("/monster/butoijo/butoijo_idle_3.png", gp.tileSize, gp.tileSize);
        left4 = loadImage("/monster/butoijo/butoijo_idle_4.png", gp.tileSize, gp.tileSize);
        right1 = loadImage("/monster/butoijo/butoijo_idle_1.png", gp.tileSize, gp.tileSize);
        right2 = loadImage("/monster/butoijo/butoijo_idle_2.png", gp.tileSize, gp.tileSize);
        right3 = loadImage("/monster/butoijo/butoijo_idle_3.png", gp.tileSize, gp.tileSize);
        right4 = loadImage("/monster/butoijo/butoijo_idle_4.png", gp.tileSize, gp.tileSize);
    }


    public void setAction() {
//        if(onPath == true)
//        {
//            int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
//            int goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;
//        }
//        else
//        {
        actionLockCounter++;

        if (actionLockCounter == 120) {
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
//        }
    }
}
