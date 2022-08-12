package main;

import object.OBJ_Heart;
import object.SuperObject;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
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
    public int commandNum = 0;
    public BufferedImage hartFull;
    public BufferedImage hartHalf;
    public BufferedImage hartBlank;

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

        // CREATE HUD OBJECT
        SuperObject heart = new OBJ_Heart(gamePanel);
        hartFull = heart.image;
        hartHalf = heart.image2;
        hartBlank = heart.image3;
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

        // TITLE STATE
        if (gamePanel.gameState == gamePanel.TITLE_STATE) {
            drawTitleScreen();
        }

        // PLAY STATE
        if (gamePanel.gameState == gamePanel.PLAY_STATE) {
            drawPlayerLife();
        }

        // 11:41
        // PAUSE STATE
        if (gamePanel.gameState == gamePanel.PAUSE_STATE) {
            drawPlayerLife();
            drawPauseScreen();
        }

        // DIALOGUE STATE
        if (gamePanel.gameState == gamePanel.DIALOG_STATAE) {
            drawPlayerLife();
            drawDialogueScreen();
        }
    }

    private void drawPlayerLife() {
        int x = gamePanel.tileSize / 2;
        int y = gamePanel.tileSize / 2;
        int i = 0;

        // DRAW BLANK HEART
        while (i < gamePanel.player.maxLife / 2) {
            g.drawImage(hartBlank, x, y,null);
            i++;
            x += gamePanel.tileSize;
        }

        // RESET
        x = gamePanel.tileSize / 2;
        y = gamePanel.tileSize / 2;
        i = 0;

        // DRAW CURRENT LIFE
        while (i < gamePanel.player.life) {
            g.drawImage(hartHalf, x, y, null);
            i++;

            if (i < gamePanel.player.life) {
                g.drawImage(hartFull, x, y, null);
            }
            i++;
            x += gamePanel.tileSize;
        }
    }

    private void drawTitleScreen() {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        // TITLE NAME
        g.setFont(g.getFont().deriveFont(Font.BOLD, 96F));
        String text = "Blue Boy Adventure";
        int x = getXforCenteredText(text);
        int y = gamePanel.tileSize * 3;

        // SHADOW
        g.setColor(Color.GRAY);
        g.drawString(text, x + 5, y + 5);

        // MAIN COLOR
        g.setColor(Color.WHITE);
        g.drawString(text, x, y);

        // BLUE BOY IMAGE
        x = gamePanel.screenWidth / 2 - (gamePanel.tileSize * 2) / 2;
        y += gamePanel.tileSize * 2;
        g.drawImage(gamePanel.player.down1, x, y, gamePanel.tileSize * 2, gamePanel.tileSize * 2, null);

        // MENU
        g.setFont(g.getFont().deriveFont(Font.BOLD, 48F));

        text = "NEW GAME";
        x = getXforCenteredText(text);
        y += gamePanel.tileSize * 3.5;
        g.drawString(text, x, y);

        if (commandNum == 0) {
            g.drawString(">", x - gamePanel.tileSize, y);
        }

        text = "LOAD GAME";
        x = getXforCenteredText(text);
        y += gamePanel.tileSize;
        g.drawString(text, x, y);

        if (commandNum == 1) {
            g.drawString(">", x - gamePanel.tileSize, y);
        }

        text = "QUIT";
        x = getXforCenteredText(text);
        y += gamePanel.tileSize;
        g.drawString(text, x, y);

        if (commandNum == 2) {
            g.drawString(">", x - gamePanel.tileSize, y);
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
