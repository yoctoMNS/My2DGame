package main;

import object.OBJ_Key;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI {
    GamePanel gamePanel;
    Font arial_40, arial_80B;
    BufferedImage keyImage;
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
        OBJ_Key key = new OBJ_Key();
        keyImage = key.image;
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g) {
        if (gameFinished) {
            g.setFont(arial_40);
            g.setColor(Color.WHITE);

            String text;
            int textLength;
            int x;
            int y;

            text = "You found the treasure!";
            textLength = (int)g.getFontMetrics().getStringBounds(text, g).getWidth();
            x = gamePanel.screenWidth / 2 - textLength / 2;
            y = gamePanel.screenHeight / 2 - (gamePanel.tileSize * 3);
            g.drawString(text, x, y);

            text = "Your time is : " + decimalFormat.format(playTime) + "!";
            textLength = (int)g.getFontMetrics().getStringBounds(text, g).getWidth();
            x = gamePanel.screenWidth / 2 - textLength / 2;
            y = gamePanel.screenHeight / 2 + (gamePanel.tileSize * 4);
            g.drawString(text, x, y);

            g.setFont(arial_80B);
            g.setColor(Color.YELLOW);
            text = "Congratulations!";
            textLength = (int)g.getFontMetrics().getStringBounds(text, g).getWidth();
            x = gamePanel.screenWidth / 2 - textLength / 2;
            y = gamePanel.screenHeight / 2 + (gamePanel.tileSize * 2);
            g.drawString(text, x, y);

            gamePanel.gameThread = null;
        } else {
            g.setFont(arial_40);
            g.setColor(Color.WHITE);
            g.drawImage(keyImage, gamePanel.tileSize / 2, gamePanel.tileSize / 2, gamePanel.tileSize, gamePanel.tileSize, null);
            g.drawString("x " + gamePanel.player.hasKey, 74, 65);

            // TIME
            playTime += (double) 1 / 60;
            g.drawString("Time: " + decimalFormat.format(playTime), gamePanel.tileSize * 11, 65);

            // MESSAGE
            if (messageOn) {
                g.setFont(g.getFont().deriveFont(30F));
                g.drawString(message, gamePanel.tileSize / 2, gamePanel.tileSize * 5);
                messageCounter++;

                if (messageCounter > 120) {
                    messageCounter = 0;
                    messageOn = false;
                }
            }
        }
    }
}
