package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    GamePanel gp;
    public boolean downPressed, leftPressed, rightPressed, upPressed, enterPressed, spacePressed, shiftPressed, ctrlPressed, onePressed, twoPressed, shotPressed;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        // Movement keys
        if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) leftPressed = true;
        if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) upPressed = true;
        if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) downPressed = true;
        if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) rightPressed = true;

        // Action keys
        if (keyCode == KeyEvent.VK_SHIFT) shiftPressed = true;
        if (keyCode == KeyEvent.VK_CONTROL) {
            if (gp.gameState == gp.PLAY && gp.player.dashCooldown == 0 && !gp.player.dashing) {
                ctrlPressed = true;
            }
        }
        if (keyCode == KeyEvent.VK_F) shotPressed = true;
        if (keyCode == KeyEvent.VK_SPACE) handleSpaceKey();

        // Enter key actions
        if (keyCode == KeyEvent.VK_ENTER) handleEnterKey();

        // Game state-specific keys
        if (keyCode == KeyEvent.VK_P) handlePauseKey();
        if (gp.gameState == gp.TITLE) handleTitleStateKeys(keyCode);
        if (gp.gameState == gp.PROFILE_SCREEN) handleProfileScreenKeys(keyCode);
        if (gp.gameState == gp.DIALOGUE && keyCode == KeyEvent.VK_ENTER) enterPressed = true;
        if (gp.gameState == gp.ITEM_DROP && (keyCode == KeyEvent.VK_ENTER || keyCode == KeyEvent.VK_ESCAPE)) gp.gameState = gp.PLAY;
        if (gp.gameState == gp.JUMPSCARE_SCREEN && keyCode == KeyEvent.VK_ENTER) gp.gameState = gp.PLAY;
        if (gp.gameState == gp.PLAY && keyCode == KeyEvent.VK_ESCAPE) gp.gameState = gp.LEVEL_SCREEN;
        if (gp.gameState == gp.PLAY && keyCode == KeyEvent.VK_F1) gp.gameState = gp.LEADERBOARD_SCREEN;
        if (gp.gameState == gp.LEVEL_SCREEN && keyCode == KeyEvent.VK_ENTER) gp.gameState = gp.PLAY;
        if (gp.gameState == gp.LEADERBOARD_SCREEN && keyCode == KeyEvent.VK_ESCAPE) gp.gameState = gp.TITLE;

        // Quiz keys
        if (gp.gameState == gp.QUIZ) {
            if (keyCode == KeyEvent.VK_1) onePressed = true;
            if (keyCode == KeyEvent.VK_2) twoPressed = true;
        }
    }

    private void handleSpaceKey() {
        spacePressed = true;

        if (gp.gameState == gp.PLAY && gp.currentMap >= 7) {
            gp.player.attacking = true;
            gp.playSE(5);
        }

        if (gp.gameState == gp.CUTSCENE) {
            gp.ui.cutsceneCounter = 0;
            gp.ui.cutsceneDuration = 0;
            gp.ui.alpha = 0;
            gp.ui.cutsceneSoundPlayed = false;
            if (gp.ui.cutsceneIndex != 3 && gp.ui.cutsceneIndex != 6 && gp.ui.cutsceneIndex != 7 && gp.ui.cutsceneIndex != 9) {
                gp.ui.cutsceneIndex++;
            }
        }
        if (gp.gameState == gp.SPLASH_SCREEN) gp.sceneManager.playScene(gp.sceneManager.SCENE_TITLE);
    }

    private void handleEnterKey() {
        enterPressed = true;

        if (gp.gameState == gp.RUNNING_TEXT) gp.gameState = gp.PLAY;
        if (gp.gameState == gp.OVERLAY_TEXT) gp.gameState = gp.PLAY;
        if (gp.sceneManager.currentScene == 8) {
            gp.eHandler.teleport(7, 20, 43, 9);
            gp.sceneManager.playScene(9);
        }
    }

    private void handlePauseKey() {
        if (gp.gameState == gp.PLAY) {
            gp.gameState = gp.PAUSED;
            gp.stopMusic();
        } else {
            gp.gameState = gp.PLAY;
            gp.playMusic(0); // 0 as a temp value, for demo purposes
        }
    }

    private void handleTitleStateKeys(int keyCode) {
        if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) gp.ui.commandNum--;
        if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) gp.ui.commandNum++;
        if (keyCode == KeyEvent.VK_ENTER) handleTitleSelection();
    }

    private void handleTitleSelection() {
        if (gp.ui.commandNum == 0) { // New Adventure option
            gp.gameState = gp.ENTER_USERNAME;
            gp.gameInputBox.setBounds(0, 0, gp.getWidth(), gp.getHeight());
            gp.setLayout(null);
            gp.add(gp.gameInputBox);
            gp.gameInputBox.setFocusable(true);
            gp.gameInputBox.requestFocusInWindow();
            gp.revalidate();
            gp.repaint();
        } else if (gp.ui.commandNum == 1) { // Load Adventure option
            gp.config.listProfile();
            gp.gameState = gp.PROFILE_SCREEN;
        } else if (gp.ui.commandNum == 2) { // Leaderboard option
            gp.gameState = gp.LEADERBOARD_SCREEN;
        } else if (gp.ui.commandNum == 3) { // Exit
            System.exit(0);
        }
    }

    private void handleProfileScreenKeys(int keyCode) {
        if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) gp.ui.commandNum--;
        if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) gp.ui.commandNum++;
        if (keyCode == KeyEvent.VK_SPACE) {
            gp.config.loadConfigFromMongoDB(gp.ui.commandNum);
            gp.gameState = gp.PLAY;
            gp.startConfigThread();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        // Movement keys
        if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) leftPressed = false;
        if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) upPressed = false;
        if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) downPressed = false;
        if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) rightPressed = false;

        // Action keys
        if (keyCode == KeyEvent.VK_ENTER) enterPressed = false;
        if (keyCode == KeyEvent.VK_SPACE) spacePressed = false;
        if (keyCode == KeyEvent.VK_SHIFT) shiftPressed = false;
        if (keyCode == KeyEvent.VK_CONTROL) ctrlPressed = false;
        if (keyCode == KeyEvent.VK_F) shotPressed = false;
    }
}
