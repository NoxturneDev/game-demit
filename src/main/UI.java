package main;

import entities.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    public Font maruMonica, purisaB;
    public String currentDialogue = "";
    public Entity currentNPC;
    public int charIndex = 0;
    public String combinedText = "";
    public String itemName = "";
    public BufferedImage itemIcon;
    public String itemDescription;

    public UI(GamePanel gp) {
        this.gp = gp;
        try {
            InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
            purisaB = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80F));
        String text = "GAME PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight / 2;
        g2.drawString(text, x, y);
    }

    public void drawDialogScreen() {
        int x = gp.tileSize * 3;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 6);
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

        if (gp.gameState == gp.PLAY) {
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
    }
}