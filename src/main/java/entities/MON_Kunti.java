package entities;

import main.GamePanel;

import java.awt.*;
import java.util.Random;

public class MON_Kunti extends Entity {
    public MON_Kunti(GamePanel gp) {
        super(gp);
        direction = "down";
        speed = 6;
        damage = new Random().nextInt(50) + 5;
        life = 100;
        maxLife = 100;
        boss = true;
        name = "Kuntilanak";

        int size = gp.tileSize * 2;
        solidArea.x = 48;
        solidArea.y = 48;
        solidArea.width = size - 48 * 2;
        solidArea.height = size - 48;

        defaultSolidAreaX = solidArea.x;
        defaultSolidAreaY = solidArea.y;
        projectile = new OBJ_Fireball(gp);
        getImage();
//        setDialogue();
    }

    public void getImage() {
        int i = 2;
        up1 = loadImage("/monster/kunti/kunti_idle_1.png", gp.tileSize * i, gp.tileSize * i);
        up2 = loadImage("/monster/kunti/kunti_idle_2.png", gp.tileSize * i, gp.tileSize * i);
        up3 = loadImage("/monster/kunti/kunti_idle_3.png", gp.tileSize * i, gp.tileSize * i);
        up4 = loadImage("/monster/kunti/kunti_idle_4.png", gp.tileSize * i, gp.tileSize * i);
        down1 = loadImage("/monster/kunti/kunti_idle_1.png", gp.tileSize * i, gp.tileSize * i);
        down2 = loadImage("/monster/kunti/kunti_idle_2.png", gp.tileSize * i, gp.tileSize * i);
        down3 = loadImage("/monster/kunti/kunti_idle_3.png", gp.tileSize * i, gp.tileSize * i);
        down4 = loadImage("/monster/kunti/kunti_idle_3.png", gp.tileSize * i, gp.tileSize * i);
        left1 = loadImage("/monster/kunti/kunti_idle_1.png", gp.tileSize * i, gp.tileSize * i);
        left2 = loadImage("/monster/kunti/kunti_idle_2.png", gp.tileSize * i, gp.tileSize * i);
        left3 = loadImage("/monster/kunti/kunti_idle_3.png", gp.tileSize * i, gp.tileSize * i);
        left4 = loadImage("/monster/kunti/kunti_idle_3.png", gp.tileSize * i, gp.tileSize * i);
        right1 = loadImage("/monster/kunti/kunti_idle_1.png", gp.tileSize * i, gp.tileSize * i);
        right2 = loadImage("/monster/kunti/kunti_idle_2.png", gp.tileSize * i, gp.tileSize * i);
        right3 = loadImage("/monster/kunti/kunti_idle_3.png", gp.tileSize * i, gp.tileSize * i);
        right4 = loadImage("/monster/kunti/kunti_idle_3.png", gp.tileSize * i, gp.tileSize * i);
    }

    public void setAction() {
        if (getTileDistance(gp.player) < 50) {
            moveTowardPlayer(60);
            checkShootOrNot(20, 30);
        } else {
            getRandomDirection(120);
        }
    }
}
