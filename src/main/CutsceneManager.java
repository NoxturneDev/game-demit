//package main;
//
//import javax.imageio.ImageIO;
//import java.io.IOException;
//
//public class CutsceneManager {
//    GamePanel gp;
//    public int SCENE_RADEN_WIJAYA = 7;
//    public int currentCutsceneIndex = 0;
//    public int currentPanelIndex;
//    public Cutscenes[] cutscenes = new Cutscenes[10];
//
//    public CutsceneManager(GamePanel gp)
//    {
//        this.gp = gp;
//    }
//
//    public void setupCutscenes()
//    {
//        try {
//            int i = 0;
//            cutscenes[SCENE_RADEN_WIJAYA] = new Cutscenes(gp);
//            cutscenes[SCENE_RADEN_WIJAYA].panels[i] = ImageIO.read(getClass().getResourceAsStream("/cutscenes/2_1.png"));
//            i++;
//            cutscenes[SCENE_RADEN_WIJAYA].panels[i] = ImageIO.read(getClass().getResourceAsStream("/cutscenes/2_2.png"));
//            i++;
//            cutscenes[SCENE_RADEN_WIJAYA].panels[i] = ImageIO.read(getClass().getResourceAsStream("/cutscenes/2_3.png"));
//            i = 0;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void playCutscenePerFrames() {
//    }
//
//    public void animateCutscene()
//    {
//        if (currentCutsceneIndex == 1) {
//            cutsceneDuration++;
//            cutsceneDuration++;
//
//            if (!cutsceneSoundPlayed) {
//                gp.playSE(21);
//                cutsceneSoundPlayed = true;
//            }
//            cutsceneHeight = cutscenes[cutsceneIndex].getHeight();
//            cutsceneWidth = cutscenes[cutsceneIndex].getWidth();
//
//            animationCutsceneType = FADE_IN;
//            slideRight(100);
//            fadeIn(100);
//        }
//
//        if (cutsceneIndex == 2) {
//            cutsceneDuration++;
//            cutsceneCounter++;
//
//            if (!cutsceneSoundPlayed) {
//                gp.playSE(22);
//                cutsceneSoundPlayed = true;
//            }
//            cutsceneHeight = cutscenes[cutsceneIndex].getHeight();
//            cutsceneWidth = cutscenes[cutsceneIndex].getWidth();
//
//            animationCutsceneType = FADE_IN;
//            slideRight(100);
//            fadeIn(100);
//        }
//
//        if (cutsceneIndex == 3) {
//            cutsceneDuration++;
//            cutsceneCounter++;
//
//            cutsceneHeight = cutscenes[cutsceneIndex].getHeight();
//            cutsceneWidth = cutscenes[cutsceneIndex].getWidth();
//
//            if (!cutsceneSoundPlayed) {
//                gp.playSE(23);
//                cutsceneSoundPlayed = true;
//            }
//            animationCutsceneType = FADE_IN;
//            zoomIn(80);
//            fadeIn(80);
//        }
//
//// end animation on 3 seconds as default animation
//        if(cutsceneIndex == 3 && cutsceneDuration > 200) {
//            gp.gameState = gp.RUNNING_TEXT;
//            currentRunningText = "TIDAAKKKKKKKK!";
//            cutsceneDuration = 0;
//        }
//
//        if (cutsceneIndex == 4) {
//            cutsceneDuration++;
//            cutsceneCounter++;
//
//            cutsceneHeight = cutscenes[cutsceneIndex].getHeight();
//            cutsceneWidth = cutscenes[cutsceneIndex].getWidth();
//
//            if (!cutsceneSoundPlayed) {
//                gp.playSE(24);
//                cutsceneSoundPlayed = true;
//            }
//            animationCutsceneType = FADE_IN;
//            slideRight(100);
//            fadeIn(100);
//        }
//        if (cutsceneIndex == 5) {
//            cutsceneDuration++;
//            cutsceneCounter++;
//
//            cutsceneHeight = cutscenes[cutsceneIndex].getHeight();
//            cutsceneWidth = cutscenes[cutsceneIndex].getWidth();
//
//            if (!cutsceneSoundPlayed) {
//                gp.playSE(25);
//                cutsceneSoundPlayed = true;
//            }
//            animationCutsceneType = FADE_IN;
//            slideRight(100);
//            fadeIn(100);
//        }
//        if (cutsceneIndex == 6) {
//            cutsceneDuration++;
//            cutsceneCounter++;
//
//            cutsceneHeight = cutscenes[cutsceneIndex].getHeight();
//            cutsceneWidth = cutscenes[cutsceneIndex].getWidth();
//
//            animationCutsceneType = 3;
//            if (!cutsceneSoundPlayed) {
//                gp.playSE(26);
//                cutsceneSoundPlayed = true;
//            }
//            zoomIn(100);
//        }
//// end animation on 3 seconds as default animation
//        if(cutsceneIndex == 6 && cutsceneDuration > 200) {
//            gp.sceneManager.playScene(5);
//        }
//
////        KERIS CUTSCENE (PROLOG 4)
//        if (cutsceneIndex == 7) {
//            cutsceneDuration++;
//            cutsceneCounter++;
//
//            cutsceneHeight = cutscenes[cutsceneIndex].getHeight();
//            cutsceneWidth = cutscenes[cutsceneIndex].getWidth();
//
//            if (!cutsceneSoundPlayed) {
//                gp.playSE(30);
//                cutsceneSoundPlayed = true;
//            }
//            animationCutsceneType = FADE_IN;
//            zoomIn(100);
//            fadeIn(100);
//        }
//
//        if(cutsceneIndex == 7 && cutsceneDuration > 200) {
//            gp.sceneManager.playScene(8);
//        }
//
////        RADEN WIJAYA CUTSCENE
//    }
//
//    public void setCurrentCutsceneIndex (int index) {
//        currentCutsceneIndex = index;
//        currentPanelIndex = 0;
//    }
//}
