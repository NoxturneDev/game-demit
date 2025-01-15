package entities;

import main.GamePanel;

import java.awt.*;

public class NPC_Raden extends Entity {
    public NPC_Raden(GamePanel gp) {
        super(gp);
        direction = "up";
        speed = 1;
        hasQuiz = true;
        quizQuestion = "Keris merupakan sebuah Senjata Tradisional asal Papua?";
        quizCorrectAnswer = 1;

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

        dialogues[1][1] = "Tahukan kamu apa itu Blankon?";
        dialogues[1][3] = "Kau bisa mencari blankon itu  di sekitar sini";
        dialogues[1][3] = "Namun berhati-hati lah..";
    }
}
