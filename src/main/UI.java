package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.text.DecimalFormat;

public class UI {
    GamePanel gamePanel;
    Graphics2D g;
    Font arial_40, arial_80B;
    public boolean messageOn;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished;
    double playTime;
    DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g) {
        this.g = g;
        g.setFont(arial_40);
        g.setColor(Color.WHITE);

        if (gamePanel.gameState == gamePanel.PLAY_STATE) {
            // Do play state stuff later
        }

        if (gamePanel.gameState == gamePanel.PAUSE_STATE) {
            drawPauseScreen();
        }
    }

    public void drawPauseScreen() {
        g.setFont(g.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gamePanel.screenHeight / 2;

        g.drawString(text, x, y);
    }

    public int getXforCenteredText(String text) {
        int length = (int) g.getFontMetrics().getStringBounds(text, g).getWidth();
        int x = gamePanel.screenWidth / 2 - length / 2;

        return x;
    }
}
