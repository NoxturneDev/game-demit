package main;

import entities.Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

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
    public String currentBossName = "Kuntilanak";


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
            pixeloid =  Font.createFont(Font.TRUETYPE_FONT, is);

            getCutScenesImage();
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getCutScenesImage() {
        try {
//            PROLOG 1 - Lanang
            cutscenes[1] = ImageIO.read(getClass().getResourceAsStream("/cutscenes/1_1.png"));
            cutscenes[2] = ImageIO.read(getClass().getResourceAsStream("/cutscenes/1_2.png"));
            cutscenes[3] = ImageIO.read(getClass().getResourceAsStream("/cutscenes/1_3.png"));
//            PROLOG 2 - Pocong
            cutscenes[4] = ImageIO.read(getClass().getResourceAsStream("/cutscenes/2_1.png"));
            cutscenes[5] = ImageIO.read(getClass().getResourceAsStream("/cutscenes/2_2.png"));
            cutscenes[6] = ImageIO.read(getClass().getResourceAsStream("/cutscenes/2_3.png"));
//            PROLOG 3 - Keris TEMPORARY
            cutscenes[7] = ImageIO.read(getClass().getResourceAsStream("/cutscenes/3_1.png"));
//            PROLOG 4 - Raden
            cutscenes[8] = ImageIO.read(getClass().getResourceAsStream("/cutscenes/5_1.png"));
            cutscenes[9] = ImageIO.read(getClass().getResourceAsStream("/cutscenes/5_2.png"));
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
        if (cutsceneIndex == 1) {
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

        if (cutsceneIndex == 2) {
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

        if (cutsceneIndex == 3) {
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

// end animation on 3 seconds as default animation
        if (cutsceneIndex == 3 && cutsceneDuration > 200) {
            gp.gameState = gp.RUNNING_TEXT;
            currentRunningText = "TIDAAKKKKKKKK!";
            cutsceneDuration = 0;
        }

        if (cutsceneIndex == 4) {
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
        if (cutsceneIndex == 5) {
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
        if (cutsceneIndex == 6) {
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
// end animation on 3 seconds as default animation
        if (cutsceneIndex == 6 && cutsceneDuration > 200) {
            gp.sceneManager.playScene(5);
        }

//        KERIS CUTSCENE (PROLOG 4)
        if (cutsceneIndex == 7) {
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

        if (cutsceneIndex == 7 && cutsceneDuration > 200) {
            gp.sceneManager.playScene(8);
        }

//        RADEN WIJAYA CUTSCENE
        if (cutsceneIndex == 8) {
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

        if (cutsceneIndex == 9) {
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

        if (cutsceneIndex == 9 && cutsceneDuration > 200) {
            gp.sceneManager.playScene(12);
        }
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

        text = "NEW ADVENTURE";
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

        // DRAW LEADERBOARD LIST
        Player firstPlace = gp.leaderboardHandler.FIRST_PLACE;
        Player secondPlace = gp.leaderboardHandler.SECOND_PLACE;
        Player thirdPlace = gp.leaderboardHandler.THIRD_PLACE;
        Player fourthPlace = gp.leaderboardHandler.FOURTH_PLACE;
        Player fifthPlace = gp.leaderboardHandler.FIFTH_PLACE;
        String[][] leaderboard = {
                {"Dila", "6", "1530"},
                {"Firschanya", "6", "1410"},
                {"Galih", "5", "1300"},
                {"Rehan", "4", "1150"},
                {"Fahmi", "2", "400"},
        };
//        String[][] leaderboard = {
//                {firstPlace.name, String.valueOf(firstPlace.level), String.valueOf(firstPlace.totalScore)},
//                {secondPlace.name, String.valueOf(secondPlace.level), String.valueOf(secondPlace.totalScore)},
//                {thirdPlace.name, String.valueOf(thirdPlace.level), String.valueOf(thirdPlace.totalScore)},
//                {fourthPlace.name, String.valueOf(fourthPlace.level), String.valueOf(fourthPlace.totalScore)},
//                {fifthPlace.name, String.valueOf(fifthPlace.level), String.valueOf(fifthPlace.totalScore)},
//        };
//
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 36F)); // Smaller font for list
        for (int i = 0; i < leaderboard.length; i++) {
            y += gp.tileSize; // Space between rows

            // Draw each column
            g2.drawString(leaderboard[i][0], column1X, y); // Player Name
            g2.drawString(leaderboard[i][1], column2X, y); // Level
            g2.drawString(leaderboard[i][2], column3X, y); // Total Score
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
        String instructions = "Press Enter to return";
        int instructionsX = getXforCenteredText(instructions);
        int instructionsY = gp.screenHeight - gp.tileSize;
        g2.drawString(instructions, instructionsX, instructionsY);
    }


    public void drawQuizScreen() {

    }

    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80F));
        String text = "GAME PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight / 2;
        g2.drawString(text, x, y);
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
            //currentDialogue = currentNPC.dialogues[npc.dialogueSet][npc.dialogueIndex];//For display text once, enable this and disable letter by letter.(Letter by letter: The if statement below there)

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
        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2;
        x += gp.tileSize / 4;
        y += gp.tileSize / 4;
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
        g2.drawString("HP: " + gp.player.HP, x, y);
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

        g2.setFont(pixeloid);
//        drawDebug(g2);
        if (gp.bossBattle) {
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
            String text = currentBossName;
            int x = getXforCenteredText(text);
            int y = gp.tileSize * 2;
            g2.setColor(red);
            g2.drawString(text, x, y);
        }

        drawStatus(g2);
        g2.setFont(castlefavor);
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
        if (gp.gameState == gp.PLAY) {
            Color c = new Color(0, 0, 0, 90);  // R,G,B, alfa(opacity)
            g2.setColor(c);
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
            drawHP();
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
        if (gp.gameState == gp.QUIZ) {
            g2.setFont(pixeloid);
            drawQuiz();
        }
    }

    public void drawStatus (Graphics2D g2) {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));
        g2.setColor(Color.white);
        g2.drawString("HP: " + gp.player.HP, 20, 20);
        g2.drawString("Level: " + gp.player.level, 20, 40);
        g2.drawString("Total Score: " + gp.player.totalScore, 20, 70);
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
