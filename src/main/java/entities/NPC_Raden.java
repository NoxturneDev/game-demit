package entities;

import main.GamePanel;

import java.awt.*;
import java.util.Random;

public class NPC_Raden extends Entity {
    public NPC_Raden(GamePanel gp) {
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

        dialogueSet = -1;
        getImage();
        setDialogue();
    }

    public void getImage() {
        up1 = loadImage("/npc/radenwijaya/radenwijaya_idle_1.png", gp.tileSize, gp.tileSize);
        up2 = loadImage("/npc/radenwijaya/radenwijaya_idle_2.png", gp.tileSize, gp.tileSize);
        up3 = loadImage("/npc/radenwijaya/radenwijaya_idle_3.png", gp.tileSize, gp.tileSize);
        up4 = loadImage("/npc/radenwijaya/radenwijaya_idle_4.png", gp.tileSize, gp.tileSize);
        left1 = loadImage("/npc/radenwijaya/radenwijaya_idle_1.png", gp.tileSize, gp.tileSize);
        left2 = loadImage("/npc/radenwijaya/radenwijaya_idle_2.png", gp.tileSize, gp.tileSize);
        left3 = loadImage("/npc/radenwijaya/radenwijaya_idle_3.png", gp.tileSize, gp.tileSize);
        left4 = loadImage("/npc/radenwijaya/radenwijaya_idle_4.png", gp.tileSize, gp.tileSize);
        right1 = loadImage("/npc/radenwijaya/radenwijaya_idle_1.png", gp.tileSize, gp.tileSize);
        right2 = loadImage("/npc/radenwijaya/radenwijaya_idle_2.png", gp.tileSize, gp.tileSize);
        right3 = loadImage("/npc/radenwijaya/radenwijaya_idle_3.png", gp.tileSize, gp.tileSize);
        right4 = loadImage("/npc/radenwijaya/radenwijaya_idle_4.png", gp.tileSize, gp.tileSize);
        down1 = loadImage("/npc/radenwijaya/radenwijaya_idle_1.png", gp.tileSize, gp.tileSize);
        down2 = loadImage("/npc/radenwijaya/radenwijaya_idle_2.png", gp.tileSize, gp.tileSize);
        down3 = loadImage("/npc/radenwijaya/radenwijaya_idle_3.png", gp.tileSize, gp.tileSize);
        down4 = loadImage("/npc/radenwijaya/radenwijaya_idle_4.png", gp.tileSize, gp.tileSize);
    }

    public void setDialogue() {
        dialogues[0][0] = "Anak muda...";
        dialogues[0][1] = "Aku adalah Raden Wijaya";
        dialogues[0][2] = "Aku akan membantumu";
        dialogues[0][3] = "Saat ini kamu sedang diincar, lanang";
        dialogues[0][4] = "Perjalanan mu kedepan akan sangat berat, bersiaplah";
        dialogues[0][5] = "Aku akan jelaskan lagi nanti";
        dialogues[0][6] = "Namun saat ini, pergilah ke hutan keramat itu";
        dialogues[0][7] = "Dan kalahkan kuntilanak tersebut";
    }

    public void setAction() {
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
