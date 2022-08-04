package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.IOException;
import java.io.InputStream;

public class UI {
    GamePanel gamePanel;
    Graphics2D g;
    Font marumonica;
    Font purisaB;
    public boolean messageOn;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished;
    public String currentDialogue = "";

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        try {
            InputStream stream = UI.class.getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
            this.marumonica = Font.createFont(Font.TRUETYPE_FONT, stream);
            stream = UI.class.getResourceAsStream("/font/Purisa Bold.ttf");
            this.purisaB = Font.createFont(Font.TRUETYPE_FONT, stream);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g) {
        this.g = g;
        g.setFont(marumonica);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setColor(Color.WHITE);

        // PLAY STATE
        if (gamePanel.gameState == gamePanel.PLAY_STATE) {
            // Do play state stuff later
        }

        // PAUSE STATE
        if (gamePanel.gameState == gamePanel.PAUSE_STATE) {
            drawPauseScreen();
        }

        // DIALOGUE STATE
        if (gamePanel.gameState == gamePanel.DIALOG_STATAE) {
            drawDialogueScreen();
        }
    }

    private void drawDialogueScreen() {
        int x = gamePanel.tileSize * 2;
        int y = gamePanel.tileSize / 2;
        int width = gamePanel.screenWidth - (gamePanel.tileSize * 4);
        int height = gamePanel.tileSize * 4;

        drawSubWindow(x, y, width, height);

        g.setFont(g.getFont().deriveFont(Font.PLAIN, 32F));
        x += gamePanel.tileSize;
        y += gamePanel.tileSize;

        for (String line : currentDialogue.split("\n")) {
            g.drawString(line, x, y);
            y += 40;
        }
    }

    public void drawPauseScreen() {
        g.setFont(g.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gamePanel.screenHeight / 2;

        g.drawString(text, x, y);
    }

    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0, 0, 0, 210);
        g.setColor(c);
        g.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g.setColor(c);
        g.setStroke(new BasicStroke(5));
        g.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

    public int getXforCenteredText(String text) {
        int length = (int) g.getFontMetrics().getStringBounds(text, g).getWidth();
        int x = gamePanel.screenWidth / 2 - length / 2;

        return x;
    }
}
