package main;

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

        // GAME STATE
        if (gamePanel.gameState == gamePanel.PLAY_STATE) {
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
