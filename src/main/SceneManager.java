package main;

public class SceneManager {
    GamePanel gp;

//    TODO: CREATE SCENE STATE LIKE GAMEPLAY STATE
    public int currentScene = 0;

    public SceneManager(GamePanel gp) {
        this.gp = gp;
    }

    public void playScene(int i) {
        if(i == 1) {
            gp.gameState = gp.TITLE;
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
            gp.gameState = gp.RUNNING_TEXT;
            gp.ui.currentRunningText = "RUN!";
        }
    }
}
