package main;

import javax.swing.JFrame;
import java.awt.Dialog;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean upPressed;
    public boolean downPressed;
    public boolean leftPressed;
    public boolean rightPressed;
    public boolean enterPressed;
    GamePanel gamePanel;

    // DEBUG
    public boolean checkDrawTime = false;

    public KeyHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // TITLE STATE
        if (gamePanel.gameState == gamePanel.TITLE_STATE) {
            if (code == KeyEvent.VK_W) {
                gamePanel.ui.commandNum--;

                if (gamePanel.ui.commandNum < 0) {
                    gamePanel.ui.commandNum = 2;
                }
            }
            if (code == KeyEvent.VK_S) {
                gamePanel.ui.commandNum++;

                if (gamePanel.ui.commandNum > 2) {
                    gamePanel.ui.commandNum = 0;
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                if (gamePanel.ui.commandNum == 0) {
                    gamePanel.gameState = gamePanel.PLAY_STATE;
                    gamePanel.playMusic(0);
                }
                if (gamePanel.ui.commandNum == 1) {
                    // add later
                }
                if (gamePanel.ui.commandNum == 2) {
                    System.exit(0);
                }
            }
        }

        // GAME STATE
        else if (gamePanel.gameState == gamePanel.PLAY_STATE) {
            if (code == KeyEvent.VK_W) {
                upPressed = true;
            }
            if (code == KeyEvent.VK_S) {
                downPressed = true;
            }
            if (code == KeyEvent.VK_A) {
                leftPressed = true;
            }
            if (code == KeyEvent.VK_D) {
                rightPressed = true;
            }
            if (code == KeyEvent.VK_P) {
                gamePanel.gameState = gamePanel.PAUSE_STATE;
            }
            if (code == KeyEvent.VK_ENTER) {
                enterPressed = true;
            }
            if (code == KeyEvent.VK_UP) {
                gamePanel.zoomInOut(1);
            }
            if (code == KeyEvent.VK_DOWN) {
                gamePanel.zoomInOut(-1);
            }

            // DEBUG
            if (code == KeyEvent.VK_T) {
                checkDrawTime = !checkDrawTime;
            }
        }

        // PAUSE STATE
        else if (gamePanel.gameState == gamePanel.PAUSE_STATE) {
            if (code == KeyEvent.VK_P) {
                gamePanel.gameState = gamePanel.PLAY_STATE;
            }
        }

        // DIALOGUE STATE
        else if (gamePanel.gameState == gamePanel.DIALOG_STATAE) {
            if (code == KeyEvent.VK_ENTER) {
                gamePanel.gameState = gamePanel.PLAY_STATE;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
    }
}
