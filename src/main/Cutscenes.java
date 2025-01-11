//package main;
//
//import java.awt.image.BufferedImage;
//
//public class Cutscenes {
//    GamePanel gp;
//    public int animationCutsceneType;
//    public int cutsceneIndex;
//    public int cutsceneDuration;
//    public int cutsceneWidth, cutsceneHeight;
//    public int cutsceneCounter;
//    public int cutsceneX, cutsceneY;
//    public boolean cutsceneSoundPlayed;
//    public BufferedImage image;
//    public BufferedImage[] panels;
//    float alpha = 0F;
//
//    public int FADE_IN = 0;
//    public int FADE_OUT = 1;
//    public int SLIDE_LEFT = 2;
//    public int SLIDE_RIGHT = 3;
//
//    public Cutscenes(GamePanel gp)
//    {
//        this.gp = gp;
//    }
//
//    public void animate() {
//        if (gp.cutsceneManager.currentPanelIndex < panels.length) {
//            cutsceneDuration++;
//            cutsceneCounter++;
//
//            cutsceneHeight = panels[gp.cutsceneManager.currentPanelIndex].getHeight();
//            cutsceneWidth = panels[gp.cutsceneManager.currentPanelIndex].getWidth();
//
//            if (!cutsceneSoundPlayed) {
//                gp.playSE(30);
//                cutsceneSoundPlayed = true;
//            }
//
//            animationCutsceneType = FADE_IN;
//            zoomIn(100);
//            fadeIn(100);
//        } else if (gp.cutsceneManager.currentPanelIndex == panels.length - 1) {
//            gp.cutsceneManager.currentPanelIndex = 0;
//        }
//    }
//
//    public void fadeIn(int duration) {
////        cutsceneWidth = gp.screenWidth;
////        cutsceneHeight = gp.screenHeight;
//
//        double progress = (double) cutsceneCounter / duration;
//
////         Calculate alpha value (0.0 to 1.0)
////        alpha += (float) cutsceneCounter ;
//        if (progress > 1.0) progress = 1.0;
//        if (alpha < 0.99) {
//            alpha += (float) progress / 100;
//        }
//    }
//
//    public void zoomIn(int duration) {
//        // Calculate progress (0 to 1)
//        double progress = (double) cutsceneCounter / duration;
//        if (progress > 1.0) progress = 1.0; // Clamp to 1.0
//
//        // Apply sinusoidal ease-out curve
//        double curveFactor = Math.sin((progress * Math.PI) / 2);
//
//        // Dynamically adjust size using the curve
//        cutsceneWidth = (int) (gp.screenWidth + 100 - curveFactor * 100);
//        cutsceneHeight = (int) (gp.screenHeight + 100 - curveFactor * 100);
//        // Calculate center position
//        int centerX = gp.screenWidth / 2;
//        int centerY = gp.screenHeight / 2;
//
//        // Calculate top-left corner of the image for centering
//        cutsceneX = centerX - (cutsceneWidth / 2);
//        cutsceneY = centerY - (cutsceneHeight / 2);
//
//    }
//}
