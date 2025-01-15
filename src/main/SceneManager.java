package main;

import objects.I_Keris;
import objects.Items;

public class SceneManager {
    GamePanel gp;

//    TODO: CREATE SCENE STATE LIKE GAMEPLAY STATE
    public int currentScene = 0;
    public int SCENE_TITLE = 1;
    public int SPLASH_SCREEN = 99;
    public int SCENE_DIALOG_AFTER_TUYUL = 10;
    public int JUMPSCARE_POCONG = 50;

    public int PROLOGUE = 20;
    public int PROLOGUE_CUTSCENE_1 = 21;
    public int PROLOGUE_CUTSCENE_2 = 22;

    public int BOSS_BATTLE_KUNTI = 50;
    public int BOSS_BATTLE_POCONG = 60;
    public int BOSS_BATTLE_BUTOIJO = 70;
    public int BOSS_BATTLE_BUTOIJO_FINISHED = 71;
    public int BOSS_BATTLE_BUTOIJO_EPILOGUE = 72;
    public int BOSS_BATTLE_KUYANG = 80;
    public int BOSS_BATTLE_NYIBLORONG = 90;

    public enum SceneIndex {
        PROLOG_CUTSCENE_1,
        PROLOG_CUTSCENE_2,
        PROLOG_CUTSCENE_3,
    }

    public SceneManager(GamePanel gp) {
        this.gp = gp;
    }

    public void playScene(int i) {
        currentScene = i;

        if (i == SPLASH_SCREEN) {
            gp.gameState = gp.SPLASH_SCREEN;
            gp.playMusic(1);
        }

        if(i == SCENE_TITLE) {
            gp.gameState = gp.TITLE;
        }

        if (i == PROLOGUE) {
            gp.ui.textOverlay = "Lanang adalah seorang anak yang sedang \nbermain di luar rumahnya. \nTiba-tiba, ia melihat sesosok pocong \nberjalan menuju rumahnya.";
            gp.gameState = gp.OVERLAY_TEXT;
        }

        if (i == 2) {
            gp.gameState = gp.PLAY;
            gp.playMusic(2);
        }

        if (i == SceneIndex.PROLOG_CUTSCENE_1.ordinal()) {
            gp.stopMusic();
            gp.ui.cutsceneIndex = UI.Cutscenes.PROLOG_1_1.ordinal();
            gp.gameState = gp.CUTSCENE;
        }

        if (i == PROLOGUE_CUTSCENE_2) {
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

//        LEVEL 1 BOSS = KUNTILANAK SCENES
        if (i == BOSS_BATTLE_KUNTI) {
            gp.bossBattle = true;
        }

//        LEVEL 2 BOSS = KUNTILANAK SCENES
        if (i == BOSS_BATTLE_POCONG) {
            gp.bossBattle = true;
        }

//        LEVEL 3 BOSS = BUTO IJO SCENES
        if (i == BOSS_BATTLE_BUTOIJO) {
            gp.ui.cutsceneIndex = 4;
            gp.gameState = gp.CUTSCENE;
            gp.bossBattle = true;
        }
        if (i == BOSS_BATTLE_BUTOIJO_FINISHED) {
            I_Keris item = new I_Keris(gp);
            gp.ui.itemName = item.itemName ;
            gp.ui.itemDescription = item.itemDescription;
            gp.ui.itemIcon = item.down1;
            gp.gameState = gp.ITEM_DROP;
        }
        if (i == BOSS_BATTLE_BUTOIJO_EPILOGUE) {
            gp.ui.textOverlay = "Butoijo telah dikalahkan! dia meninggalkan\n keris yang dijatuhkan oleh bapak.";
            gp.gameState = gp.OVERLAY_TEXT;
            gp.player.LEVEL_3_LOCKED = false;
        }

        if (i == BOSS_BATTLE_KUYANG) {
            gp.bossBattle = true;
        }
        if (i == BOSS_BATTLE_NYIBLORONG) {
            gp.bossBattle = true;
        }
    }
}
