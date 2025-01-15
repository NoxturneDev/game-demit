package entities;

import main.GamePanel;

import java.awt.*;
import java.util.Random;

public class MON_Pocong extends Entity {
    public MON_Pocong(GamePanel gp) {
        super(gp);
        direction = "down";
        speed = 3;
        damage = new Random().nextInt(20)+5;
        life = 20;

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
        up1 = loadImage("/monster/pocong/pocong_idle_1.png", gp.tileSize, gp.tileSize);
        up2 = loadImage("/monster/pocong/pocong_idle_2.png", gp.tileSize, gp.tileSize);
        up3 = loadImage("/monster/pocong/pocong_idle_3.png", gp.tileSize, gp.tileSize);
        down1 = loadImage("/monster/pocong/pocong_idle_1.png", gp.tileSize, gp.tileSize);
        down2 = loadImage("/monster/pocong/pocong_idle_2.png", gp.tileSize, gp.tileSize);
        down3 = loadImage("/monster/pocong/pocong_idle_3.png", gp.tileSize, gp.tileSize);
        left1 = loadImage("/monster/pocong/pocong_idle_1.png", gp.tileSize, gp.tileSize);
        left2 = loadImage("/monster/pocong/pocong_idle_2.png", gp.tileSize, gp.tileSize);
        left3 = loadImage("/monster/pocong/pocong_idle_3.png", gp.tileSize, gp.tileSize);
        right1 = loadImage("/monster/pocong/pocong_idle_1.png", gp.tileSize, gp.tileSize);
        right2 = loadImage("/monster/pocong/pocong_idle_2.png", gp.tileSize, gp.tileSize);
        right3 = loadImage("/monster/pocong/pocong_idle_3.png", gp.tileSize, gp.tileSize);
    }

    public void setAction() {
        if(onPath == true)
        {

            //Check if it stops chasing
            checkStopChasingOrNot(gp.player,15,100);

            //Search the direction to go
            searchPath(getGoalCol(gp.player), getGoalRow(gp.player));

            //Check if it shoots a projectile
//            checkShootOrNot(200, 30); //Just added to red slimes
        }
        else
        {
            //Check if it starts chasing
            checkStartChasingOrNot(gp.player, 5, 100);

            //Get a random direction
            getRandomDirection(120);
        }
    }
}
