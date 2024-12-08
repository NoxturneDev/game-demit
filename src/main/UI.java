package main;

import entities.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    public Font maruMonica, purisaB;
    public int commandNum = 0;
    public String currentDialogue = "";
    public Entity currentNPC;
    public int charIndex = 0;
    public String combinedText = "";
    public String itemName = "";
    public BufferedImage itemIcon;
    public String itemDescription;
    public int jumpscareDuration;
    public int[] jumpscares = new int[10];
    public BufferedImage[] cutscenes = new BufferedImage[100];
    public int cutsceneIndex;
    public int cutsceneDuration;
    public int cutsceneWidth, cutsceneHeight;
    public int cutsceneCounter;
    public int cutsceneX, cutsceneY;

    public UI(GamePanel gp) {
        this.gp = gp;
        try {
            InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
            purisaB = Font.createFont(Font.TRUETYPE_FONT, is);

            getCutScenesImage();
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getCutScenesImage() {
        try {
            for (int i = 1; i < 3; i++) { // temporary available cutscenes
                cutscenes[i] = ImageIO.read(getClass().getResourceAsStream("/cutscenes/cutscene_" + i + ".jpg"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showCutScene(int i) {
        g2.drawImage(cutscenes[cutsceneIndex], cutsceneX, cutsceneY, cutsceneWidth, cutsceneHeight, null);
    }

    public void animateCutscene() {
        cutsceneDuration++;
        cutsceneWidth = gp.screenWidth;
        cutsceneHeight = gp.screenHeight;

        for (int j = 0; j < 1000; j++) {
            cutsceneWidth -= j;
            cutsceneHeight -= j;
            g2.drawImage(cutscenes[cutsceneIndex], 0, 0, cutsceneWidth, cutsceneHeight, null);
        }

        if (cutsceneDuration > 360) {
            gp.gameState = gp.PLAY;
            cutsceneDuration = 0;
        }
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
        g2.setColor(Color.gray);
        g2.drawString(text, x + 5, y + 5);
        //MAIN COLOR
        g2.setColor(Color.white);
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

        text = "QUIT!";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (commandNum == 2) {
            g2.drawString(">", x - gp.tileSize, y);
        }
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
        g2.setColor(Color.white);
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
        g2.setFont(maruMonica);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);  // Anti Aliasing // Smoothes the text
        g2.setColor(Color.white);

        drawDebug(g2);
        if(gp.gameState == gp.TITLE) {
            drawTitleScreen();
        }
        if (gp.gameState == gp.PLAY) {
            Color c = new Color(0, 0, 0, 90);  // R,G,B, alfa(opacity)
            g2.setColor(c);
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
            drawHP();
        }
        if (gp.gameState == gp.DIALOGUE) {
            drawDialogScreen();
        }
        if (gp.gameState == gp.PAUSED) {
            drawPauseScreen();
        }
        if (gp.gameState == gp.DIED) {
            drawGameOverScreen();
        }
        if (gp.gameState == gp.ITEM_DROP) {
            drawItemDropScreen();
        }
        if (gp.gameState == gp.JUMPSCARE_SCREEN) {
            drawFullScreenImage("/tuyul/tuyul_npc_jumpscare.png");
        }
        if (gp.gameState == gp.CUTSCENE) {
            showCutScene(1);
        }
    }

    //    DEBUG FUNCTION
    public void drawDebug(Graphics2D g2) {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));
        g2.setColor(Color.white);
        g2.drawString("X: " + gp.player.worldX + " Y: " + gp.player.worldY, 10, 20);
        g2.drawString("Screen X: " + gp.player.screenX + "Screen Y: " + gp.player.screenY, 10, 40);
        g2.drawString("Cutscene width: " + cutsceneWidth + " Cutscene height: " + cutsceneHeight, 10, 60);
    }

    public void update() {
        if (gp.gameState == gp.CUTSCENE) {
            cutsceneDuration++;
            cutsceneCounter++;

            // Calculate progress (0 to 1)
            double progress = (double) cutsceneCounter / 360;
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


            if (cutsceneDuration > 360) {
                gp.gameState = gp.PLAY;
                cutsceneDuration = 0;
            }
        }
    }
}
