package entities;

import main.GamePanel;

import java.awt.*;
import java.util.Random;

public class MON_Blorong extends Entity {
    public MON_Blorong(GamePanel gp) {
        super(gp);
        direction = "down";
        speed = 2;
        damage = new Random().nextInt(20) + 5;
        life = 100;
        boss = true;

        int size = gp.tileSize * 3;
        solidArea = new Rectangle();
        solidArea.x = 64;
        solidArea.y = 64;
        solidArea.width = size - 64;
        solidArea.height = size - 64;

        defaultSolidAreaX = 64;
        defaultSolidAreaY = 64;
//        projectile = new OBJ_Fireball(gp);
        getImage();
//        setDialogue();
    }

    public void getImage() {
        int i = 3;
        up1 = loadImage("/monster/blorong/blorong_idle_1.png", gp.tileSize * i, gp.tileSize * i);
        up2 = loadImage("/monster/blorong/blorong_idle_2.png", gp.tileSize * i, gp.tileSize * i);
        up3 = loadImage("/monster/blorong/blorong_idle_3.png", gp.tileSize * i, gp.tileSize * i);
        up4 = loadImage("/monster/blorong/blorong_idle_4.png", gp.tileSize * i, gp.tileSize * i);
        down1 = loadImage("/monster/blorong/blorong_idle_1.png", gp.tileSize * i, gp.tileSize * i);
        down2 = loadImage("/monster/blorong/blorong_idle_2.png", gp.tileSize * i, gp.tileSize * i);
        down3 = loadImage("/monster/blorong/blorong_idle_3.png", gp.tileSize * i, gp.tileSize * i);
        down4 = loadImage("/monster/blorong/blorong_idle_4.png", gp.tileSize * i, gp.tileSize * i);
        left1 = loadImage("/monster/blorong/blorong_idle_1.png", gp.tileSize * i, gp.tileSize * i);
        left2 = loadImage("/monster/blorong/blorong_idle_2.png", gp.tileSize * i, gp.tileSize * i);
        left3 = loadImage("/monster/blorong/blorong_idle_3.png", gp.tileSize * i, gp.tileSize * i);
        left4 = loadImage("/monster/blorong/blorong_idle_4.png", gp.tileSize * i, gp.tileSize * i);
        right1 = loadImage("/monster/blorong/blorong_idle_1.png", gp.tileSize * i, gp.tileSize * i);
        right2 = loadImage("/monster/blorong/blorong_idle_2.png", gp.tileSize * i, gp.tileSize * i);
        right3 = loadImage("/monster/blorong/blorong_idle_3.png", gp.tileSize * i, gp.tileSize * i);
        right4 = loadImage("/monster/blorong/blorong_idle_4.png", gp.tileSize * i, gp.tileSize * i);
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

//        int i = new Random().nextInt(100)*1;
//        if (i < 50) {
//            checkAttackOrNot(30, gp.tileSize * 4, gp.tileSize);
//        }
    }
}
