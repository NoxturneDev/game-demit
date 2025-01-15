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
        SPLASH_SCREEN,
        TITLE_SCREEN,
        GAME_START,
        PROLOG_OVERLAY_BEGINNING,
        PROLOG_CUTSCENE_1,
        PROLOG_CUTSCENE_2,
        PROLOG_CUTSCENE_3,
        PROLOG_RUNNING_TEXT_AFTER_CUTSCENE_1,
        PROLOG_RUNNING_TEXT_AFTER_CUTSCENE_2,
        PROLOG_RUNNING_TEXT_AFTER_CUTSCENE_3,
        PROLOG_CUTSCENE_4, // get KERIS
        PROLOG_ITEM_DROP_KERIS,
        PROLOG_RUNNING_TEXT_AFTER_GET_KERIS,
        PROLOG_RUNNING_TEXT_AFTER_TUYUL,
        PROLOG_CUTSCENE_5, // prabu reveal
        LEVEL_1_CUTSCENE_REVEAL,
        LEVEL_1_JUMPSCARE,
        LEVEL_1_ITEM_DROP,
        LEVEL_1_OVERLAY_RUNNING_TEXT,
    }

    public SceneManager(GamePanel gp) {
        this.gp = gp;
    }

    public void playScene(int i) {
        currentScene = i;

        if (i == SceneIndex.SPLASH_SCREEN.ordinal()) {
            gp.gameState = gp.SPLASH_SCREEN;
            gp.playMusic(1);
        }

        if(i == SceneIndex.TITLE_SCREEN.ordinal()) {
            gp.gameState = gp.TITLE;
        }

        if (i == SceneIndex.PROLOG_OVERLAY_BEGINNING.ordinal()) {
            gp.ui.textOverlay = "Lanang adalah seorang anak yang sedang \nbermain di luar rumahnya. \nTiba-tiba, ia melihat sesosok pocong \nberjalan menuju rumahnya.";
            gp.gameState = gp.OVERLAY_TEXT;
        }

        if (i == SceneIndex.GAME_START.ordinal()) {
            gp.gameState = gp.PLAY;
            gp.playMusic(2);
        }

        if (i == SceneIndex.PROLOG_CUTSCENE_1.ordinal()) {
            gp.stopMusic();
            gp.ui.cutsceneIndex = UI.Cutscenes.PROLOG_1_1.ordinal();
            gp.gameState = gp.CUTSCENE;
        }

        if (i == SceneIndex.PROLOG_RUNNING_TEXT_AFTER_CUTSCENE_1.ordinal()) {
            gp.ui.currentRunningText = "TIDAKK KELUARGAKUUUU! KENAPAA INI? TOLONG!!";
            gp.gameState = gp.RUNNING_TEXT;
        }

        if (i == SceneIndex.PROLOG_CUTSCENE_2.ordinal()) {
            gp.stopMusic();
            gp.ui.cutsceneIndex = UI.Cutscenes.PROLOG_2_1.ordinal();
            gp.gameState = gp.CUTSCENE;
        }

        if (i == SceneIndex.PROLOG_RUNNING_TEXT_AFTER_CUTSCENE_2.ordinal()) {
            gp.ui.currentRunningText = "LARIIIIII ADA POCONGG!!!";
            gp.gameState = gp.RUNNING_TEXT;
        }

        if (i == SceneIndex.PROLOG_CUTSCENE_3.ordinal()) {
            gp.ui.cutsceneDuration = 0;
            gp.stopMusic();
            gp.ui.cutsceneIndex = UI.Cutscenes.PROLOG_3_1.ordinal();
            gp.gameState = gp.CUTSCENE;
        }

        if (i == SceneIndex.PROLOG_RUNNING_TEXT_AFTER_CUTSCENE_3.ordinal()) {
            gp.gameState = gp.RUNNING_TEXT;
            gp.ui.currentRunningText = "Aku mengingat sesautu, harus kembali ke rumah!";
        }

        if (i == SceneIndex.PROLOG_CUTSCENE_4.ordinal()) {
            gp.ui.cutsceneIndex = UI.Cutscenes.PROLOG_4_1.ordinal();
            gp.gameState = gp.CUTSCENE;
        }

        if (i == SceneIndex.PROLOG_ITEM_DROP_KERIS.ordinal()) {
            gp.stopMusic();
            I_Keris keris = new I_Keris(gp);
            gp.ui.itemName = keris.itemName;
            gp.ui.itemDescription = keris.itemDescription;
            gp.ui.itemIcon = keris.itemIcon;
            gp.gameState = gp.ITEM_DROP;
        }

        if (i == SceneIndex.PROLOG_RUNNING_TEXT_AFTER_GET_KERIS.ordinal()) {
            gp.ui.currentRunningText = "Kenapa bermunculan banyak sekali tuyul\n. Aku harus meghabisi mereka satu persatu\n";
            gp.gameState = gp.RUNNING_TEXT;
            gp.playSE(32);
        }

        if (i == SceneIndex.PROLOG_RUNNING_TEXT_AFTER_TUYUL.ordinal()) {
            gp.ui.currentRunningText = "Sepertinya tuyul-tuyul tadi tertarik oleh\n keris yang ditinggalkan oleh\n bapak...";
            gp.gameState = gp.RUNNING_TEXT;
        }

        if (i == SceneIndex.PROLOG_CUTSCENE_5.ordinal()) {
            gp.ui.cutsceneIndex = UI.Cutscenes.PROLOG_5_1.ordinal();
            gp.gameState = gp.CUTSCENE;
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
