package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;

public class KeyHandler implements KeyListener {
    GamePanel gp;
    public boolean downPressed, leftPressed, rightPressed, upPressed, enterPressed, spacePressed;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
            leftPressed = true;
        }
        if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
            upPressed = true;
        }
        if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
            downPressed = true;
        }
        if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        }
        if (keyCode == KeyEvent.VK_ENTER) {
            enterPressed = true;
            if (gp.gameState == gp.RUNNING_TEXT) {
                gp.gameState = gp.PLAY;
            }
        }
        if (keyCode == KeyEvent.VK_SPACE) {
            spacePressed = true;
            if (gp.gameState == gp.PLAY) {
                gp.player.attacking = true; // temporary fixed bug when stuck on attack animation when space is pressed
                gp.playSE(1);
            }
            if (gp.gameState == gp.CUTSCENE) {
                gp.ui.cutsceneCounter = 0;
                gp.ui.cutsceneDuration = 0;
                gp.ui.alpha = 0;
                gp.ui.cutsceneSoundPlayed = false;
                gp.ui.cutsceneIndex++;
            }
        }

        if (keyCode == KeyEvent.VK_P) {
            if (gp.gameState == gp.PLAY) {
                gp.gameState = gp.PAUSED;
                gp.stopMusic();
            } else {
                gp.gameState = gp.PLAY;
                gp.playMusic(0); //0 as a temp value, for demo purpose
            }
        }

        if (gp.gameState == gp.TITLE) {
            if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
                gp.ui.commandNum--;
            }
            if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
                gp.ui.commandNum++;
            }

            if (keyCode == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNum == 0) {
                    gp.gameState = gp.RUNNING_TEXT;
                    gp.ui.currentRunningText = "Lanang adalah seorang yang";
                    gp.stopMusic();
                    gp.playMusic(2);
                }
                if (gp.ui.commandNum == 1) {
                    System.exit(0);
                }
            }
        }

        if (gp.gameState == gp.DIALOGUE) {
            if (keyCode == KeyEvent.VK_ENTER) {
                enterPressed = true;
            }
        }

        if (gp.gameState == gp.ITEM_DROP) {
            if(keyCode == KeyEvent.VK_ENTER || keyCode == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.PLAY;
            }
        }

        if (gp.gameState == gp.JUMPSCARE_SCREEN) {
            if (keyCode == KeyEvent.VK_ENTER) {
                gp.gameState = gp.PLAY;
            }
        }
//        if (keyCode == KeyEvent.VK_ENTER) {
//            if (gp.gameState == gp.DIALOGUE) {
//                gp.gameState = gp.PLAY;
//            }
//        }

        if (gp.gameState == gp.QUIZ) {
            if (keyCode == KeyEvent.VK_1) {
                gp.gameState = gp.PLAY;
            }
            if (keyCode == KeyEvent.VK_2) {
                gp.gameState = gp.PLAY;
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }
        if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
            upPressed = false;
        }
        if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
            downPressed = false;
        }
        if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }
        if (keyCode == KeyEvent.VK_ENTER) {
            enterPressed = false;
        }
        if (keyCode == KeyEvent.VK_SPACE) {
            spacePressed = false;
        }
    }
}
