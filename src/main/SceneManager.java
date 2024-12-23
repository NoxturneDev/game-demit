package main;

import objects.I_Keris;
import objects.Items;

public class SceneManager {
    GamePanel gp;

//    TODO: CREATE SCENE STATE LIKE GAMEPLAY STATE
    public int currentScene = 0;
    public int SCENE_TITLE = 1;
    public int SCENE_DIALOG_AFTER_TUYUL = 10;
    public int JUMPSCARE_POCONG = 50;

    public SceneManager(GamePanel gp) {
        this.gp = gp;
    }
    public void playScene(int i) {
        currentScene = i;

        if(i == SCENE_TITLE) {
            gp.gameState = gp.LEADERBOARD_SCREEN;
            gp.playMusic(1);
        }
        if (i == 2) {
            gp.gameState = gp.PLAY;
            gp.playMusic(2);
        }
        if (i == 3) {
            gp.stopMusic();
            gp.ui.cutsceneIndex = 1;
            gp.gameState = gp.CUTSCENE;
        }
        if (i == 4) {
//            POCONG CUTSCENE
            gp.stopMusic();
            gp.ui.cutsceneIndex = 4;
            gp.gameState = gp.CUTSCENE;
        }
        if (i == 5) {
//            AFTER POCONG CUTSCENE
            gp.stopMusic();
            gp.ui.currentRunningText = "RUN!";
            gp.gameState = gp.RUNNING_TEXT;
        }
        if (i == 6) {
            gp.gameState = gp.RUNNING_TEXT;
            gp.ui.currentRunningText = "Aku harus kembali ke rumah!";
        }
        if (i == 7) {
            gp.ui.cutsceneDuration = 0;
            gp.stopMusic();
            gp.ui.cutsceneIndex = 7;
            gp.gameState = gp.CUTSCENE;
        }

        if (i == 8) {
            // temp item
            currentScene = 8;
            I_Keris keris = new I_Keris(gp);
            gp.ui.itemName = keris.itemName;
            gp.ui.itemDescription = keris.itemDescription;
            gp.ui.itemIcon = keris.itemIcon;
            gp.gameState = gp.ITEM_DROP;
        }

        if (i == 9) {
            gp.ui.currentRunningText = "Kenapa bermunculan banyak sekali tuyul\n. Aku harus meghabisi mereka satu persatu\n";
            gp.gameState = gp.RUNNING_TEXT;
            gp.playSE(32);
        }

        if (i == SCENE_DIALOG_AFTER_TUYUL) {
            gp.ui.currentRunningText = "Sepertinya tuyul-tuyul tadi tertarik oleh\n keris yang ditinggalkan oleh\n bapak...";
            gp.gameState = gp.RUNNING_TEXT;
            gp.leaderboardHandler.addRecord(gp.player);
        }

        if (i == 11) {
            gp.ui.cutsceneIndex = 8;
            gp.gameState = gp.CUTSCENE;
        }

        if (i == 12) {
            gp.eHandler.teleport(4, 23, 20, -1);
            gp.gameState = gp.PLAY;
        }

        if (i == JUMPSCARE_POCONG) {
            gp.ui.currentJumpscarePath = "/tuyul/tuyul_npc_jumpscare.png";
            gp.playSE(26);
            gp.gameState = gp.JUMPSCARE_SCREEN;
        }
    }
}
