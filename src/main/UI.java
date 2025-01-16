package main;

import entities.Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    SceneManager cm;
    public Font maruMonica, purisaB, alucrads, pixeloid, castlefavor;
    public Color red = new Color(176, 4, 12);
    public Color shadowRed = new Color(94, 3, 7);
    public int commandNum = 0;
    public int quizNum = 0;
    public String currentDialogue = "";
    public Entity currentNPC;
    public int charIndex = 0;
    public String combinedText = "";
    public String currentRunningText;
    public String currentQuiz = "";
    public int currentQuizCorrectAnswer = 0;
    public String itemName = "";
    public BufferedImage itemIcon;
    public String itemDescription;
    public String currentJumpscarePath;
    public BufferedImage[] cutscenes = new BufferedImage[100];
    public String textOverlay;
    public String[] textOverlayList = new String[100];


    public int animationCutsceneType;
    public int cutsceneIndex;
    public int cutsceneDuration;
    public int cutsceneWidth, cutsceneHeight;
    public int cutsceneCounter;
    public int cutsceneX, cutsceneY;
    public int FADE_IN = 0;
    public int FADE_OUT = 1;
    public int SLIDE_LEFT = 2;
    public int SLIDE_RIGHT = 3;

    //    CUTSCENE DICTIONARY
    public enum Cutscenes {
        PROLOG_1_1,
        PROLOG_1_2,
        PROLOG_1_3,
        //        POCONG
        PROLOG_2_1,
        PROLOG_2_2,
        PROLOG_2_3,
        //        FLASHBACK
        PROLOG_3_1,
        PROLOG_3_2,
        //        KERIS
        PROLOG_4_1,
        //        RADEN WIJAYA
        PROLOG_5_1,
        PROLOG_5_2,
        //        LEVEL 1 (Kunti)
        LEVEL_1_1,
        LEVEL_1_2,
        //        LEVEL 2 (KUYANG)
        LEVEL_2_1,
        LEVEL_2_2,
        //        LEVEL 3 (BUTO IJO)
        LEVEL_3_1,
        LEVEL_3_2,
        //        LEVEL 4 (NYI BLORONG)
        LEVEL_4_1,
        LEVEL_4_2,
        //        BEFORE LAST BOSS

        EPILOG_1_1,
        EPILOG_1_2,
        //        LAST BOSS
        BOSS_REVEAL,
        EPILOG_2_1,
        LAST_BOSS_1_1,
        LAST_BOSS_1_2,
        LAST_BOSS_2_1,
        LAST_BOSS_2_2,
        //        reveal nyi roro kidul
        LAST_BOSS_3_1,
        //        ENDING
        ENDING,
        ENDING_1_1,
        ENDING_1_2,
        ENDING_1_3,
        ENDING_2_1,
        ENDING_2_2,
    }

    public UI(GamePanel gp) {
        this.gp = gp;
        try {
            InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
            purisaB = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/Alucrads-aYVK5.ttf");
            alucrads = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/Castlefavor-OGLWP.ttf");
            castlefavor = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/PixeloidMono-d94EV.ttf");
            pixeloid = Font.createFont(Font.TRUETYPE_FONT, is);

            getCutScenesImage();
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getCutScenesImage() {
        try {
//            PROLOG
            cutscenes[Cutscenes.PROLOG_1_1.ordinal()] = ImageIO.read(getClass().getResourceAsStream("/cutscenes/PROLOG_1_1.png"));
            cutscenes[Cutscenes.PROLOG_1_2.ordinal()] = ImageIO.read(getClass().getResourceAsStream("/cutscenes/PROLOG_1_2.png"));
            cutscenes[Cutscenes.PROLOG_1_3.ordinal()] = ImageIO.read(getClass().getResourceAsStream("/cutscenes/PROLOG_1_3.png"));
            cutscenes[Cutscenes.PROLOG_2_1.ordinal()] = ImageIO.read(getClass().getResourceAsStream("/cutscenes/PROLOG_2_1.png"));
            cutscenes[Cutscenes.PROLOG_2_2.ordinal()] = ImageIO.read(getClass().getResourceAsStream("/cutscenes/PROLOG_2_2.png"));
            cutscenes[Cutscenes.PROLOG_2_3.ordinal()] = ImageIO.read(getClass().getResourceAsStream("/cutscenes/PROLOG_2_3.png"));
            cutscenes[Cutscenes.PROLOG_3_1.ordinal()] = ImageIO.read(getClass().getResourceAsStream("/cutscenes/PROLOG_3_1.png"));
            cutscenes[Cutscenes.PROLOG_3_2.ordinal()] = ImageIO.read(getClass().getResourceAsStream("/cutscenes/PROLOG_3_2.png"));
            cutscenes[Cutscenes.PROLOG_4_1.ordinal()] = ImageIO.read(getClass().getResourceAsStream("/cutscenes/PROLOG_4_1.png"));
            cutscenes[Cutscenes.PROLOG_5_1.ordinal()] = ImageIO.read(getClass().getResourceAsStream("/cutscenes/PROLOG_5_1.png"));
            cutscenes[Cutscenes.PROLOG_5_2.ordinal()] = ImageIO.read(getClass().getResourceAsStream("/cutscenes/PROLOG_5_2.png"));
//            LEVEL 1
//            BOSS

            cutscenes[Cutscenes.EPILOG_1_1.ordinal()] = ImageIO.read(getClass().getResourceAsStream("/cutscenes/EPILOG_1_1.png"));
            cutscenes[Cutscenes.EPILOG_1_2.ordinal()] = ImageIO.read(getClass().getResourceAsStream("/cutscenes/EPILOG_1_2.png"));
            cutscenes[Cutscenes.BOSS_REVEAL.ordinal()] = ImageIO.read(getClass().getResourceAsStream("/cutscenes/BOSS_REVEAL.jpg"));
            cutscenes[Cutscenes.EPILOG_2_1.ordinal()] = ImageIO.read(getClass().getResourceAsStream("/cutscenes/EPILOG_2_1.jpg"));

            cutscenes[Cutscenes.ENDING.ordinal()] = ImageIO.read(getClass().getResourceAsStream("/cutscenes/ENDING.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drawCutsceneImage() {
        float alpha = 0.5F; //draw half transparent
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        g2.setComposite(ac);
        g2.drawImage(cutscenes[cutsceneIndex], cutsceneX, cutsceneY, cutsceneWidth, cutsceneHeight, null);
    }

    float alpha = 0F;

    public void showCutScene(int i) {
        g2.setColor(Color.white);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        if (animationCutsceneType == FADE_IN || animationCutsceneType == FADE_OUT) {
            AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
            g2.setComposite(ac);
        }

        g2.drawImage(cutscenes[cutsceneIndex], cutsceneX, cutsceneY, cutsceneWidth, cutsceneHeight, null);
    }

    public void fadeIn(int duration) {
//        cutsceneWidth = gp.screenWidth;
//        cutsceneHeight = gp.screenHeight;

        double progress = (double) cutsceneCounter / duration;

//         Calculate alpha value (0.0 to 1.0)
//        alpha += (float) cutsceneCounter ;
        if (progress > 1.0) progress = 1.0;
        if (alpha < 0.99) {
            alpha += (float) progress / 100;
        }
    }

    public boolean cutsceneSoundPlayed = false;

    public void animateCutscene() {
//        PROLOG 1
        if (cutsceneIndex == Cutscenes.PROLOG_1_1.ordinal()) {
            cutsceneDuration++;
            cutsceneCounter++;

            if (!cutsceneSoundPlayed) {
                gp.playSE(21);
                cutsceneSoundPlayed = true;
            }
            cutsceneHeight = cutscenes[cutsceneIndex].getHeight();
            cutsceneWidth = cutscenes[cutsceneIndex].getWidth();

            animationCutsceneType = FADE_IN;
            slideRight(100);
            fadeIn(100);
        }

        if (cutsceneIndex == Cutscenes.PROLOG_1_2.ordinal()) {
            cutsceneDuration++;
            cutsceneCounter++;

            if (!cutsceneSoundPlayed) {
                gp.playSE(22);
                cutsceneSoundPlayed = true;
            }
            cutsceneHeight = cutscenes[cutsceneIndex].getHeight();
            cutsceneWidth = cutscenes[cutsceneIndex].getWidth();

            animationCutsceneType = FADE_IN;
            slideRight(100);
            fadeIn(100);
        }

        if (cutsceneIndex == Cutscenes.PROLOG_1_3.ordinal()) {
            cutsceneDuration++;
            cutsceneCounter++;

            cutsceneHeight = cutscenes[cutsceneIndex].getHeight();
            cutsceneWidth = cutscenes[cutsceneIndex].getWidth();

            if (!cutsceneSoundPlayed) {
                gp.playSE(23);
                cutsceneSoundPlayed = true;
            }
            animationCutsceneType = FADE_IN;
            zoomIn(80);
            fadeIn(80);
        }

        if (cutsceneIndex == Cutscenes.PROLOG_1_3.ordinal() && cutsceneDuration > 200) {
            gp.sceneManager.playScene(SceneManager.SceneIndex.PROLOG_RUNNING_TEXT_AFTER_CUTSCENE_1.ordinal());
        }

//        PROLOG 2 - Pocong
        if (cutsceneIndex == Cutscenes.PROLOG_2_1.ordinal()) {
            cutsceneDuration++;
            cutsceneCounter++;

            cutsceneHeight = cutscenes[cutsceneIndex].getHeight();
            cutsceneWidth = cutscenes[cutsceneIndex].getWidth();

            if (!cutsceneSoundPlayed) {
                gp.playSE(24);
                cutsceneSoundPlayed = true;
            }
            animationCutsceneType = FADE_IN;
            slideRight(100);
            fadeIn(100);
        }
        if (cutsceneIndex == Cutscenes.PROLOG_2_2.ordinal()) {
            cutsceneDuration++;
            cutsceneCounter++;

            cutsceneHeight = cutscenes[cutsceneIndex].getHeight();
            cutsceneWidth = cutscenes[cutsceneIndex].getWidth();

            if (!cutsceneSoundPlayed) {
                gp.playSE(25);
                cutsceneSoundPlayed = true;
            }
            animationCutsceneType = FADE_IN;
            slideRight(100);
            fadeIn(100);
        }
        if (cutsceneIndex == Cutscenes.PROLOG_2_3.ordinal()) {
            cutsceneDuration++;
            cutsceneCounter++;

            cutsceneHeight = cutscenes[cutsceneIndex].getHeight();
            cutsceneWidth = cutscenes[cutsceneIndex].getWidth();

            animationCutsceneType = 3;
            if (!cutsceneSoundPlayed) {
                gp.playSE(26);
                cutsceneSoundPlayed = true;
            }
            zoomIn(100);
        }
        if (cutsceneIndex == Cutscenes.PROLOG_2_3.ordinal() && cutsceneDuration > 200) {
            gp.sceneManager.playScene(SceneManager.SceneIndex.PROLOG_RUNNING_TEXT_AFTER_CUTSCENE_2.ordinal());
        }

//        PROLOG 3 - Flashback
        if (cutsceneIndex == Cutscenes.PROLOG_3_1.ordinal()) {
            cutsceneDuration++;
            cutsceneCounter++;

            cutsceneHeight = cutscenes[cutsceneIndex].getHeight();
            cutsceneWidth = cutscenes[cutsceneIndex].getWidth();

            if (!cutsceneSoundPlayed) {
                gp.playSE(24);
                cutsceneSoundPlayed = true;
            }
            animationCutsceneType = FADE_IN;
            slideRight(100);
            fadeIn(100);
        }

        if (cutsceneIndex == Cutscenes.PROLOG_3_2.ordinal()) {
            cutsceneDuration++;
            cutsceneCounter++;

            cutsceneHeight = cutscenes[cutsceneIndex].getHeight();
            cutsceneWidth = cutscenes[cutsceneIndex].getWidth();

            if (!cutsceneSoundPlayed) {
                gp.playSE(24);
                cutsceneSoundPlayed = true;
            }
            animationCutsceneType = FADE_IN;
            slideRight(100);
            fadeIn(100);
        }
        if (cutsceneIndex == Cutscenes.PROLOG_3_2.ordinal() && cutsceneDuration > 200) {
            gp.sceneManager.playScene(SceneManager.SceneIndex.PROLOG_RUNNING_TEXT_AFTER_CUTSCENE_3.ordinal());
        }

//        PROLOG 4 - Keris
        if (cutsceneIndex == Cutscenes.PROLOG_4_1.ordinal()) {
            cutsceneDuration++;
            cutsceneCounter++;

            cutsceneHeight = cutscenes[cutsceneIndex].getHeight();
            cutsceneWidth = cutscenes[cutsceneIndex].getWidth();

            if (!cutsceneSoundPlayed) {
                gp.playSE(30);
                cutsceneSoundPlayed = true;
            }
            animationCutsceneType = FADE_IN;
            zoomIn(100);
            fadeIn(100);
        }
        if (cutsceneIndex == Cutscenes.PROLOG_4_1.ordinal() && cutsceneDuration > 200) {
            gp.sceneManager.playScene(SceneManager.SceneIndex.PROLOG_ITEM_DROP_KERIS.ordinal());
        }

//          PROLOG 5 - Raden Wijaya
        if (cutsceneIndex == Cutscenes.PROLOG_5_1.ordinal()) {
            cutsceneDuration++;
            cutsceneCounter++;

            cutsceneHeight = cutscenes[cutsceneIndex].getHeight();
            cutsceneWidth = cutscenes[cutsceneIndex].getWidth();

            if (!cutsceneSoundPlayed) {
//                gp.playSE(30);
                cutsceneSoundPlayed = true;
            }
            animationCutsceneType = FADE_IN;
            slideRight(100);
            fadeIn(100);
        }

        if (cutsceneIndex == Cutscenes.PROLOG_5_2.ordinal()) {
            cutsceneDuration++;
            cutsceneCounter++;

            cutsceneHeight = cutscenes[cutsceneIndex].getHeight();
            cutsceneWidth = cutscenes[cutsceneIndex].getWidth();

            if (!cutsceneSoundPlayed) {
//                gp.playSE(30);
                cutsceneSoundPlayed = true;
            }
            animationCutsceneType = FADE_IN;
            slideRight(200);
            fadeIn(200);
        }

        if (cutsceneIndex == Cutscenes.PROLOG_5_2.ordinal() && cutsceneDuration > 200) {
            gp.sceneManager.playScene(12);
        }

        if (cutsceneIndex == Cutscenes.EPILOG_2_1.ordinal()) {
            cutsceneDuration++;
            cutsceneCounter++;

            cutsceneHeight = cutscenes[cutsceneIndex].getHeight();
            cutsceneWidth = cutscenes[cutsceneIndex].getWidth();

            if (!cutsceneSoundPlayed) {
//                gp.playSE(30);
                cutsceneSoundPlayed = true;
            }
            animationCutsceneType = FADE_IN;
            slideRight(200);
            fadeIn(200);
        }

        if (cutsceneIndex == Cutscenes.EPILOG_1_1.ordinal()) {
            cutsceneDuration++;
            cutsceneCounter++;

            cutsceneHeight = cutscenes[cutsceneIndex].getHeight();
            cutsceneWidth = cutscenes[cutsceneIndex].getWidth();

            if (!cutsceneSoundPlayed) {
//                gp.playSE(30);
                cutsceneSoundPlayed = true;
            }
            animationCutsceneType = FADE_IN;
            slideRight(200);
            fadeIn(200);
        }

        if (cutsceneIndex == Cutscenes.EPILOG_1_2.ordinal()) {
            cutsceneDuration++;
            cutsceneCounter++;

            cutsceneHeight = cutscenes[cutsceneIndex].getHeight();
            cutsceneWidth = cutscenes[cutsceneIndex].getWidth();

            if (!cutsceneSoundPlayed) {
//                gp.playSE(30);
                cutsceneSoundPlayed = true;
            }
            animationCutsceneType = FADE_IN;
            slideRight(200);
            fadeIn(200);
        }

        if (cutsceneIndex == Cutscenes.BOSS_REVEAL.ordinal()) {
            cutsceneDuration++;
            cutsceneCounter++;

            cutsceneHeight = cutscenes[cutsceneIndex].getHeight();
            cutsceneWidth = cutscenes[cutsceneIndex].getWidth();

            if (!cutsceneSoundPlayed) {
//                gp.playSE(30);
                cutsceneSoundPlayed = true;
            }
            animationCutsceneType = FADE_IN;
            slideRight(200);
            fadeIn(200);
        }

        if (cutsceneIndex == Cutscenes.EPILOG_2_1.ordinal()) {
            cutsceneDuration++;
            cutsceneCounter++;

            cutsceneHeight = cutscenes[cutsceneIndex].getHeight();
            cutsceneWidth = cutscenes[cutsceneIndex].getWidth();

            if (!cutsceneSoundPlayed) {
//                gp.playSE(30);
                cutsceneSoundPlayed = true;
            }
            animationCutsceneType = FADE_IN;
            slideRight(300);
            fadeIn(300);
        }

        if (cutsceneIndex == Cutscenes.EPILOG_2_1.ordinal() && cutsceneDuration > 400) {
            gp.gameState = gp.PLAY;
        }

//        if (cutsceneIndex == Cutscenes.ENDING.ordinal()) {
//            cutsceneDuration++;
//            cutsceneCounter++;
//
//            cutsceneHeight = cutscenes[cutsceneIndex].getHeight();
//            cutsceneWidth = cutscenes[cutsceneIndex].getWidth();
//
//            if (!cutsceneSoundPlayed) {
////                gp.playSE(30);
//                cutsceneSoundPlayed = true;
//            }
//            animationCutsceneType = FADE_IN;
//            slideRight(200);
//            fadeIn(200);
//        }
//
//        if (cutsceneIndex == Cutscenes.ENDING.ordinal() && cutsceneDuration > 1000) {
//            gp.gameState = gp.TITLE;
//        }
    }

    public void slideRight(int duration) {
//        cutsceneWidth = gp.screenWidth;
//        cutsceneHeight = gp.screenHeight;
        // Calculate progress (0 to 1)
        double progress = (double) cutsceneCounter / duration;
        if (progress > 1.0) progress = 1.0;

        // Ease-in-out curve for smoothness
        double curveFactor = Math.sin(progress * Math.PI / 2);

        // Calculate the X position
        int startX = gp.screenWidth; // Start off-screen to the left
        int endX = (gp.screenWidth - cutscenes[cutsceneIndex].getWidth()) / 2; // Center position
        cutsceneX = (int) (startX + (endX - startX) * curveFactor);

        // Vertically center the image
        cutsceneY = (gp.screenHeight - cutscenes[cutsceneIndex].getHeight()) / 2;
    }

    public void zoomIn(int duration) {
        // Calculate progress (0 to 1)
        double progress = (double) cutsceneCounter / duration;
        if (progress > 1.0) progress = 1.0; // Clamp to 1.0

        // Apply sinusoidal ease-out curve
        double curveFactor = Math.sin((progress * Math.PI) / 2);

        // Dynamically adjust size using the curve
        cutsceneWidth = (int) (gp.screenWidth + 100 - curveFactor * 100);
        cutsceneHeight = (int) (gp.screenHeight + 100 - curveFactor * 100);
        // Calculate center position
        int centerX = gp.screenWidth / 2;
        int centerY = gp.screenHeight / 2;

        // Calculate top-left corner of the image for centering
        cutsceneX = centerX - (cutsceneWidth / 2);
        cutsceneY = centerY - (cutsceneHeight / 2);

    }

    public void zoomOut() {
        // Calculate progress (0 to 1)
        double progress = (double) cutsceneCounter / 360;
        if (progress > 1.0) progress = 1.0; // Clamp to 1.0

        // Apply sinusoidal ease-out curve
        double curveFactor = Math.sin((progress * Math.PI) / 2);

        // Dynamically adjust size using the curve
        cutsceneWidth = (int) (gp.screenWidth + 100 + curveFactor * 100);
        cutsceneHeight = (int) (gp.screenHeight + 100 + curveFactor * 100);
        // Calculate center position
        int centerX = gp.screenWidth / 2;
        int centerY = gp.screenHeight / 2;

        // Calculate top-left corner of the image for centering
        cutsceneX = centerX - (cutsceneWidth / 2);
        cutsceneY = centerY - (cutsceneHeight / 2);

    }

    public void drawTitleScreen() {
        g2.setColor(new Color(0, 0, 0));             // FILL BACKGROUND BLACK
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        //MAIN MENU
        //TITLE NAME
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
        String text = "Demit's\n";
        int x = getXforCenteredText(text);
        int y = gp.tileSize * 3;
        //SHADOW
        g2.setColor(shadowRed);
        g2.drawString(text, x + 5, y + 5);
        //MAIN COLOR
        g2.setColor(red);
        g2.drawString(text, x, y);

        //BLUE BOY IMAGE
        x = gp.screenWidth / 2 - (gp.tileSize * 2) / 2;
        y += gp.tileSize * 2;
        g2.drawImage(gp.player.down1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);

        //MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));

        text = "NEW ADVENTURE PROFILE";
        x = getXforCenteredText(text);
        y += gp.tileSize * 3.5;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        text = "LOAD SAVE FILE";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        text = "LEADERBOARD";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (commandNum == 2) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        text = "QUIT!";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (commandNum == 3) {
            g2.drawString(">", x - gp.tileSize, y);
        }
    }

    public void drawEndingScreen() {
        g2.setColor(new Color(0, 0, 0));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 25F));
        g2.setColor(red);
        g2.drawString("ESC to back", 20, 40);

        // TITLE
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
        String title = "THE END...?";
        int x = getXforCenteredText(title);
        int y = gp.tileSize * 5;
        // SHADOW
        g2.setColor(shadowRed);
        g2.drawString(title, x + 5, y + 5);
        // MAIN COLOR
        g2.setColor(red);
        g2.drawString(title, x, y);
    }

    public void drawLeaderboardScreen() {
        g2.setColor(new Color(0, 0, 0));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 25F));
        g2.setColor(red);
        g2.drawString("ESC to back", 20, 40);

        // TITLE
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
        String title = "LEADERBOARD";
        int x = getXforCenteredText(title);
        int y = gp.tileSize * 2;
        // SHADOW
        g2.setColor(shadowRed);
        g2.drawString(title, x + 5, y + 5);
        // MAIN COLOR
        g2.setColor(red);
        g2.drawString(title, x, y);

        // LEADERBOARD HEADER
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        String headerPlayer = "PLAYER NAME";
        String headerLevel = "LEVEL";
        String headerScore = "TOTAL SCORE";

        y += gp.tileSize * 2; // Space below the title

        int column1X = gp.tileSize * 2; // Left padding for Player Name
        int column2X = gp.screenWidth / 2 - gp.tileSize; // Center for Level
        int column3X = gp.screenWidth - gp.tileSize * 5; // Right padding for Total Score

        g2.drawString(headerPlayer, column1X, y);
        g2.drawString(headerLevel, column2X, y);
        g2.drawString(headerScore, column3X, y);

        // FETCH LEADERBOARD DATA
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 36F)); // Smaller font for list
        for (int i = 0; i < gp.config.leaderboard.size(); i++) {
            y += gp.tileSize; // Space between rows

            // Draw each column
            g2.drawString(gp.config.leaderboard.get(i)[0], column1X, y); // Player Name
            g2.drawString(gp.config.leaderboard.get(i)[1], column2X, y); // Level
            g2.drawString(gp.config.leaderboard.get(i)[2], column3X, y); // Total Score
        }
    }

    private void drawLevelScreen() {
        // Background overlay
        Color background = new Color(0, 0, 0, 200);  // Semi-transparent black
        g2.setColor(background);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // Title text
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        g2.setColor(Color.WHITE);
        String title = "Levels";
        int titleX = getXforCenteredText(title);
        int titleY = gp.tileSize * 2;
        g2.drawString(title, titleX, titleY);

        // Level list
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 36F));
        int startX = gp.tileSize * 4;
        int startY = gp.tileSize * 3;
        int lineHeight = gp.tileSize * 1;

        // Render each level
        for (int i = 1; i <= 7; i++) {
            boolean isLocked = false;

            // Check the lock status for each level
            switch (i) {
                case 1 -> isLocked = gp.player.LEVEL_1_LOCKED;
                case 2 -> isLocked = gp.player.LEVEL_2_LOCKED;
                case 3 -> isLocked = gp.player.LEVEL_3_LOCKED;
                case 4 -> isLocked = gp.player.LEVEL_4_LOCKED;
                case 5 -> isLocked = gp.player.LEVEL_5_LOCKED;
                case 6 -> isLocked = gp.player.LEVEL_6_LOCKED;
                case 7 -> isLocked = gp.player.LEVEL_7_LOCKED;
            }

            // Display level and status
            String levelText = "LEVEL " + i;
            String statusText = isLocked ? "LOCKED" : "UNLOCKED";
            int levelX = startX;
            int statusX = gp.screenWidth - gp.tileSize * 6; // Adjust for alignment to the right
            int y = startY + (lineHeight * (i - 1));

            // Draw level name
            g2.setColor(Color.WHITE);
            g2.drawString(levelText, levelX, y);

            // Draw status with appropriate color
            g2.setColor(isLocked ? Color.RED : Color.GREEN);
            g2.drawString(statusText, statusX, y);
        }

        // Instructions
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F));
        g2.setColor(Color.WHITE);
        String instructions = "Press ESC to return";
        int instructionsX = getXforCenteredText(instructions);
        int instructionsY = gp.screenHeight - gp.tileSize;
        g2.drawString(instructions, instructionsX, instructionsY);
    }

    private void drawProfileScreen() {
        // Background overlay
        Color background = new Color(0, 0, 0, 200); // Semi-transparent black
        g2.setColor(background);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // Title text
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        g2.setColor(Color.WHITE);
        String title = "Profiles";
        int titleX = getXforCenteredText(title);
        int titleY = gp.tileSize * 2;
        g2.drawString(title, titleX, titleY);

        // Profile list
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 36F));
        int startX = gp.tileSize * 4;
        int startY = gp.tileSize * 3;
        int lineHeight = gp.tileSize * 1;

        // Mocked profiles for illustration (Replace with dynamic data from MongoDB)
        // Render each profile
        for (int i = 0; i < gp.config.profiles.size(); i++) {
            ProfileData profile = gp.config.profiles.get(i);

            // Display profile and status
            String username = profile.username;
            String details = "Score: " + profile.totalScore + " | Level: " + profile.level;
            int profileX = startX;
            int detailsX = gp.screenWidth - gp.tileSize * 8; // Adjust alignment to the right
            int y = startY + (lineHeight * i);


            // Draw profile name
            g2.setColor(Color.WHITE);
            g2.drawString(username, profileX, y);

            // Draw details
            g2.setColor(Color.YELLOW);
            g2.drawString(details, detailsX, y);

            if (commandNum == i) {
                g2.drawString(">", profileX - gp.tileSize, y);
            }
            // Draw status with appropriate color
//            g2.setColor(isCurrent ? Color.BLUE : Color.GREEN);
        }

        // Instructions
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F));
        g2.setColor(Color.WHITE);
        String instructions = "Use UP/DOWN to navigate, TAB to select, ESC to return";
        int instructionsX = getXforCenteredText(instructions);
        int instructionsY = gp.screenHeight - gp.tileSize;
        g2.drawString(instructions, instructionsX, instructionsY);
    }


    public void drawSplahScreen() {
//        draw big title in the middle
        g2.setColor(new Color(0, 0, 0));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
        String text = "Demit's";
        int x = getXforCenteredText(text);
        int y = gp.tileSize * 5;
        g2.setColor(red);
        g2.drawString(text, x, y);


        // Instructions
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F));
        g2.setColor(Color.WHITE);
        String instructions = "Press Space to return";
        int instructionsX = getXforCenteredText(instructions);
        int instructionsY = gp.screenHeight - gp.tileSize;
        g2.drawString(instructions, instructionsX, instructionsY);
    }


    public void drawQuizScreen() {

    }

    public void drawPauseScreen() {
        // Set font for the title
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80F));
        String text = "GAME PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight / 3; // Position it closer to the top
        g2.drawString(text, x, y);

        // Set font for the command list
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 40F));
        y += gp.tileSize * 2; // Add some space below the title

        String[] commands = {
                "F1  - Show Leaderboard",
                "F2  - Show Levels Unlocked",
                "F5  - Save Game",
                "W/A/S/D - Move",
                "SPACE - Attack",
                "F   - Fireball",
                "CTRL - Dodge"
        };

        // Display the commands line by line
        for (String command : commands) {
            x = getXforCenteredText(command); // Center each line of text
            g2.drawString(command, x, y);
            y += gp.tileSize; // Space between each line
        }
    }


    public void drawInGameTextScreen() {
        int x = 0;
        int y = gp.tileSize * 8;
        int width = gp.screenWidth;
        int height = gp.tileSize * 4;
        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
        x += gp.tileSize;
        y += gp.tileSize;
        g2.drawString(currentRunningText, x, y);

        for (String line : currentRunningText.split("\n"))   // splits dialogue until "\n" as a line
        {
            g2.drawString(line, x, y);
            y += 40;
        }
    }

//    public void drawOverlayText() {
////        black background
//        g2.setColor(new Color(0, 0, 0, 200));
//        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
//
//        int x = 0;
//        int y = 0;
//
//        g2.setColor(Color.WHITE);
//        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
//        x += gp.tileSize;
//        y += gp.tileSize;
//        for (String line : textOverlay.split("\n"))   // splits dialogue until "\n" as a line
//        {
//            g2.drawString(line, x, y);
//            y += gp.tileSize;
//        }
//    }

    // Add this variable to track the scrolling offset
    private int scrollOffset = 0;

    public void drawOverlayText() {
        // Semi-transparent black background
        if (scrollOffset == 0 && gp != null) { // Initialize only if gp is available
            scrollOffset = -gp.screenHeight / 2;
        }

        g2.setColor(new Color(0, 0, 0, 150)); // 150 is the alpha value for transparency
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        int x = gp.tileSize;
        int y = gp.tileSize - scrollOffset; // Start position adjusted by scroll offset

        // White text
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));

        // Draw each line of text
        for (String line : textOverlay.split("\n")) { // Splits dialogue until "\n" as a line
            g2.drawString(line, x, y);
            y += gp.tileSize;
        }

        // Update the scrolling offset
        scrollOffset += 1; // Adjust this value to control the scrolling speed

        // Reset the scrolling when all text is off-screen
        if (y - gp.tileSize >= gp.screenHeight) {
            scrollOffset = -gp.screenHeight / 2; // Start halfway down the screen
        }
    }


    public void drawQuiz() {
        int x = 0;
        int y = gp.tileSize * 8;
        int width = gp.screenWidth;
        int height = gp.tileSize * 4;

        // Draw the quiz box
        drawSubWindow(x, y, width, height);

        // Set font and starting position for text
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
        x += gp.tileSize; // Add padding
        y += gp.tileSize; // Add padding

        // Draw the question
        g2.drawString(currentQuiz, x, y);

        // Draw the options
        y += gp.tileSize; // Move to the next line
        g2.drawString("1. Benar", x, y);

        y += gp.tileSize; // Move to the next line
        g2.drawString("2. Salah", x, y);

        if (gp.keyH.onePressed == true) {
            gp.gameState = gp.RUNNING_TEXT;

            if (currentQuizCorrectAnswer == 1) {
                currentRunningText = "Benar sekali anak muda!";
                gp.player.totalScore += 100;
                gp.currentActiveNPC.quizHasDone = true;
            } else {
                currentRunningText = "Kamu salah! Perbaiki lagi";
            }
            gp.keyH.onePressed = false;
        }

        if (gp.keyH.twoPressed == true) {
            gp.gameState = gp.RUNNING_TEXT;

            if (currentQuizCorrectAnswer == 2) {
                currentRunningText = "Benar sekali anak muda!";
                gp.player.totalScore += 100;
            } else {
                currentRunningText = "Kamu salah, perbanyak lagi ilmu mu!";
            }
            gp.keyH.twoPressed = false;
        }
    }

    public void drawDialogScreen() {
        int x = 0;
        int y = gp.tileSize * 8;
        int width = gp.screenWidth;
        int height = gp.tileSize * 4;

        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
        x += gp.tileSize;
        y += gp.tileSize;
        g2.drawString(currentDialogue, x, y);

        if (currentNPC.dialogues[currentNPC.dialogueSet][currentNPC.dialogueIndex] != null) {
//            currentDialogue = currentNPC.dialogues[npc.dialogueSet][npc.dialogueIndex];//For display text once, enable this and disable letter by letter.(Letter by letter: The if statement below there)

            char characters[] = currentNPC.dialogues[currentNPC.dialogueSet][currentNPC.dialogueIndex].toCharArray();

            if (charIndex < characters.length) {
//                gp.playSE(17);//Speak sound
                String s = String.valueOf(characters[charIndex]);
                combinedText = combinedText + s; //every loop add one character to combinedText
                currentDialogue = combinedText;

                charIndex++;
            }
            if (gp.keyH.enterPressed == true) {
                charIndex = 0;
                combinedText = "";
                if (gp.gameState == gp.DIALOGUE /* || gp.gameState == gp.CUTSCENE*/) {
                    currentNPC.dialogueIndex++;
                    gp.keyH.enterPressed = false;
                }
            }
        } else //If no text is in the array
        {
            currentNPC.dialogueIndex = 0;
            if (gp.gameState == gp.DIALOGUE) {
                gp.gameState = gp.PLAY;
            }
        }


        for (String line : currentDialogue.split("\n"))   // splits dialogue until "\n" as a line
        {
            g2.drawString(line, x, y);
            y += 40;
        }

    }

    public void drawRunningTextScreen() {
        int x = 0;
        int y = 0;
        g2.setColor(new Color(0, 0, 0));             // FILL BACKGROUND BLACK
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
        x += gp.tileSize;
        y += gp.tileSize;
        g2.drawString(currentDialogue, x, y);

        if (currentRunningText != null) {
            //currentDialogue = currentNPC.dialogues[npc.dialogueSet][npc.dialogueIndex];//For display text once, enable this and disable letter by letter.(Letter by letter: The if statement below there)

            char characters[] = currentRunningText.toCharArray();

            if (charIndex < characters.length) {
//                gp.playSE(17);//Speak sound
                String s = String.valueOf(characters[charIndex]);
                combinedText = combinedText + s; //every loop add one character to combinedText
                currentDialogue = combinedText;

                charIndex++;
            }
//            if (gp.keyH.enterPressed == true) {
//                charIndex = 0;
//                combinedText = "";
//                if (gp.gameState == gp.DIALOGUE /* || gp.gameState == gp.CUTSCENE*/) {
//                    currentNPC.dialogueIndex++;
//                    gp.keyH.enterPressed = false;
//                }
//            }
        }


        for (String line : currentDialogue.split("\n"))   // splits dialogue until "\n" as a line
        {
            g2.drawString(line, x, y);
            y += 40;
        }
    }

    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0, 0, 0, 210);  // R,G,B, alfa(opacity)
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));    // 5 = width of outlines of graphics
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);

    }

    public void drawHP() {
        double oneScale = (double) gp.tileSize * 4 / 100; // Calculate HP unit length
        double hpBarValue = oneScale * gp.player.life;

        // Set new position for the HP bar (small gap from the top-left corner)
        int x = 10; // Small gap from the left
        int y = 30; // Small gap from the top

        if (hpBarValue < 0) {
            hpBarValue = 0; // Ensure HP value doesn't go below 0
        }

        // Draw the HP bar background
        g2.setColor(new Color(35, 35, 35));
        g2.fillRect(x - 1, y - 1, gp.tileSize * 2 + 2, 22);

        // Draw the HP bar
        g2.setColor(new Color(255, 0, 30));
        g2.fillRect(x, y, (int) hpBarValue, 20);

        // Draw the username above the HP bar
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24f));
        g2.setColor(Color.white);
        g2.drawString(gp.player.username, x + 4, y - 5);
    }

    public void drawMonsterLife() {
        //Monster HP Bar
        for (int i = 0; i < gp.monsters[1].length; i++) {
            Entity monster = gp.monsters[gp.currentMap][i];

            if (monster != null && monster.inCamera() == true) {
                if (monster.hpBarOn == true && monster.boss == false) {
                    double oneScale = (double) gp.tileSize / monster.maxLife; // (bar lenght / maxlife) Ex: if monster hp = 2, tilesize = 48px. So, 1 hp = 24px
                    double hpBarValue = oneScale * monster.life;

                    if (hpBarValue < 0) //Ex: You attack 5 hp to monster which has 3 hp. Monster's hp will be -2 and bar will ofset to left. To avoid that check if hpBarValue less than 0.
                    {
                        hpBarValue = 0;
                    }

                    g2.setColor(new Color(35, 35, 35));
                    g2.fillRect(monster.getScreenX() - 1, monster.getScreenY() - 16, gp.tileSize + 2, 12);

                    g2.setColor(new Color(255, 0, 30));
                    g2.fillRect(monster.getScreenX(), monster.getScreenY() - 15, (int) hpBarValue, 10);

                    monster.hpBarCounter++;
                    if (monster.hpBarCounter > 600)  // 10
                    {
                        monster.hpBarCounter = 0;
                        monster.hpBarOn = false;
                    }
                } else if (monster.boss == true) {
                    double oneScale = (double) gp.tileSize * 8 / monster.maxLife; // (bar lenght / maxlife) Ex: if monster hp = 2, tilesize = 48px. So, 1 hp = 24px
                    double hpBarValue = oneScale * monster.life;
                    int x = gp.screenWidth / 2 - gp.tileSize * 4;
                    int y = gp.tileSize * 10;

                    if (hpBarValue < 0)  //Ex: You attack 5 hp to monster which has 3 hp. Monster's hp will be -2 and bar will ofset to left. To avoid that check if hpBarValue less than 0.
                    {
                        hpBarValue = 0;
                    }

                    g2.setColor(new Color(35, 35, 35));
                    g2.fillRect(x - 1, y - 1, gp.tileSize * 8 + 2, 22);

                    g2.setColor(new Color(255, 0, 30));
                    g2.fillRect(x, y, (int) hpBarValue, 20);

                    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24f));
                    g2.setColor(Color.white);
                    g2.drawString(monster.name, x + 4, y - 10);
                }
            }
        }
    }

    public void drawGameOverScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80F));
        String text = "GAME OVER";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight / 2;
        g2.drawString(text, x, y);
    }

    public void drawItemDropScreen() {
        int width = gp.screenWidth - (gp.tileSize * 6);
        int height = gp.screenHeight - (gp.tileSize * 2);
        int x = gp.tileSize * 3;
        int y = gp.tileSize;
        Color c = new Color(0, 0, 0, 210);  // R,G,B, alfa(opacity)
        g2.setColor(c);
        g2.fillRect(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
        String text = itemName + "\n";
        x = getXforCenteredText(text);
        y = gp.tileSize * 3;
        //SHADOW
        g2.setColor(Color.gray);
        g2.drawString(text, x + 5, y + 5);
        //MAIN COLOR
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

//        ICON IMAGE
        x = gp.screenWidth / 2 - (gp.tileSize * 2) / 2;
        y += gp.tileSize;
        g2.drawImage(itemIcon, x, y, gp.tileSize * 2, gp.tileSize * 2, null);

//        DESCRIPTION
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
        x = gp.tileSize * 4;
        y += gp.tileSize * 3;

        for (String line : itemDescription.split("\n"))   // splits dialogue until "\n" as a line
        {
            g2.drawString(line, x, y);
            y += 40;
        }
    }

    public void drawFullScreenImage(String imagePath) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        g2.drawImage(image, -50, 0, gp.screenWidth, gp.screenHeight, null);
    }

    public int getXforCenteredText(String text) {
        int textLenght;
        textLenght = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth(); // Gets width of text.
        int x = gp.screenWidth / 2 - textLenght / 2;
        return x;
    }

    public int getXforAlignToRight(String text, int tailX) {
        int textLenght;
        textLenght = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth(); // Gets width of text.
        int x = tailX - textLenght;
        return x;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);  // Anti Aliasing // Smoothes the text
        g2.setColor(Color.RED);

        g2.setFont(maruMonica);
        drawDebug(g2);
//        if (gp.bossBattle) {
//            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
//            String text = ;
//            int x = getXforCenteredText(text);
//            int y = gp.tileSize * 2;
//            g2.setColor(red);
//            g2.drawString(text, x, y);
//        }

        if (gp.gameState == gp.ENTER_USERNAME) {
            return;
        }
        if (gp.gameState == gp.TITLE) {
            drawTitleScreen();
        }
        if (gp.gameState == gp.SPLASH_SCREEN) {
            drawSplahScreen();
        }
        if (gp.gameState == gp.LEADERBOARD_SCREEN) {
            drawLeaderboardScreen();
        }
        if (gp.gameState == gp.LEVEL_SCREEN) {
            drawLevelScreen();
        }
        if (gp.gameState == gp.PROFILE_SCREEN) {
            drawProfileScreen();
        }
        if (gp.gameState == gp.PLAY) {
            Color c = new Color(0, 0, 0, 90);  // R,G,B, alfa(opacity)
            g2.setColor(c);
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
            drawMonsterLife();
            drawStatus(g2);
        }
        if (gp.gameState == gp.DIALOGUE) {
            g2.setFont(pixeloid);
            drawDialogScreen();
        }
        if (gp.gameState == gp.PAUSED) {
            drawPauseScreen();
        }
        if (gp.gameState == gp.DIED) {
            drawGameOverScreen();
        }
        if (gp.gameState == gp.ITEM_DROP) {
            g2.setFont(pixeloid);
            drawItemDropScreen();
        }
        if (gp.gameState == gp.JUMPSCARE_SCREEN) {
            drawFullScreenImage(currentJumpscarePath);
        }
        if (gp.gameState == gp.CUTSCENE) {
            showCutScene(1);
        }
        if (gp.gameState == gp.RUNNING_TEXT) {
            g2.setFont(pixeloid);
            drawInGameTextScreen();
        }
        if (gp.gameState == gp.OVERLAY_TEXT || gp.gameState == gp.OVERLAY_ENDING_TEXT) {
            g2.setFont(pixeloid);
            drawOverlayText();
        }
        if (gp.gameState == gp.QUIZ) {
            g2.setFont(pixeloid);
            drawQuiz();
        }
        if (gp.gameState == gp.ENDING_SCREEN) {
            drawEndingScreen();
        }
    }

    public void drawStatus(Graphics2D g2) {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));
        g2.setColor(Color.white);
        drawHP();
        g2.drawString("Level: " + gp.player.level, 20, 75);
        g2.drawString("Total Score: " + gp.player.totalScore, 20, 100);
    }

    //    DEBUG FUNCTION
    public void drawDebug(Graphics2D g2) {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));
        g2.setColor(Color.white);
        g2.drawString("World X: " + gp.player.worldX + " World Y: " + gp.player.worldY, 600, 20);
        g2.drawString("Screen X: " + gp.player.screenX + " Screen Y: " + gp.player.screenY, 600, 40);
        g2.drawString("Current map: " + gp.currentMap, 600, 60);
        g2.drawString("totalScore: " + gp.player.totalScore, 600, 80);
        g2.drawString("current Monster left: " + gp.aSetter.totalMonsterMap7, 600, 100);
        g2.drawString("Col: " + gp.player.getCol() + " Row: " + gp.player.getRow(), 600, 120);
    }

    public void update() {
        if (gp.gameState == gp.CUTSCENE) {
            animateCutscene();
        }
    }
}
