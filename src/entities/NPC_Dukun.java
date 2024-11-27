package entities;

import main.GamePanel;

import java.awt.*;
import java.util.Random;

public class NPC_Dukun extends Entity{
    public NPC_Dukun(GamePanel gp) {
        super(gp);
        direction = "up";
        speed = 1;

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;

        defaultSolidAreaX = 8;
        defaultSolidAreaY = 16;

        dialogueSet = 0;
        getImage();
        setDialogue();
    }

    public void getImage() {
        up1 = loadImage("/npc/dukun/dukun_npc.png", gp.tileSize, gp.tileSize);
        up2 = loadImage("/npc/dukun/dukun_npc.png", gp.tileSize, gp.tileSize);
        down1 = loadImage("/npc/dukun/dukun_npc.png", gp.tileSize, gp.tileSize);
        down2 = loadImage("/npc/dukun/dukun_npc.png", gp.tileSize, gp.tileSize);
        left1 = loadImage("/npc/dukun/dukun_npc.png", gp.tileSize, gp.tileSize);
        left2 = loadImage("/npc/dukun/dukun_npc.png", gp.tileSize, gp.tileSize);
        right1 = loadImage("/npc/dukun/dukun_npc.png", gp.tileSize, gp.tileSize);
        right2 = loadImage("/npc/dukun/dukun_npc.png", gp.tileSize, gp.tileSize);
    }

    public void setDialogue() {
        dialogues[0][0] = "Anak muda...";
        dialogues[0][1] = "Aku adalah Raden Wijaya";
        dialogues[0][2] = "Aku akan membantumu";
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
                direction = "right";
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

    public void speak() {
        startDialogue(this, dialogueSet);
        dialogueSet++;

        if (dialogues[dialogueSet][0] == null) {
            dialogueSet = 0;
        }
    }
}
