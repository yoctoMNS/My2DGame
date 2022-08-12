package main;

import java.awt.Rectangle;

public class EventHandler {
    GamePanel gamePanel;
    Rectangle eventRect;
    int eventRectDefaultX;
    int eventRectDefaultY;

    public EventHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        eventRect = new Rectangle();
        eventRect.x = 23;
        eventRect.y = 23;
        eventRect.width = 2;
        eventRect.height = 2;
        eventRectDefaultX = eventRect.x;
        eventRectDefaultY = eventRect.y;
    }

    public void checkEvent() {
        if (hit(27, 16, "right")) teleport(gamePanel.DIALOG_STATAE);
        if (hit(23, 12, "up")) healingPool(gamePanel.DIALOG_STATAE);
    }

    private void teleport(int gameState) {
        gamePanel.gameState = gameState;
        gamePanel.ui.currentDialogue = "Teleport!";
        gamePanel.player.worldX = gamePanel.tileSize * 37;
        gamePanel.player.worldY = gamePanel.tileSize * 10;
    }

    private void damagePit(int gameState) {
        gamePanel.gameState = gameState;
        gamePanel.ui.currentDialogue = "You fall into a pit!";
        gamePanel.player.life--;

    }

    public boolean hit(int eventCol, int eventRow, String reqDirection) {
        boolean hit = false;

        gamePanel.player.solidArea.x = gamePanel.player.worldX + gamePanel.player.solidArea.x;
        gamePanel.player.solidArea.y = gamePanel.player.worldY + gamePanel.player.solidArea.y;
        eventRect.x = eventCol * gamePanel.tileSize + eventRect.x;
        eventRect.y = eventRow * gamePanel.tileSize + eventRect.y;

        if (gamePanel.player.solidArea.intersects(eventRect)) {
            if (gamePanel.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                hit = true;
            }
        }

        gamePanel.player.solidArea.x = gamePanel.player.solidAreaDefaultX;
        gamePanel.player.solidArea.y = gamePanel.player.solidAreaDefaultY;
        eventRect.x = eventRectDefaultX;
        eventRect.y = eventRectDefaultY;

        return hit;
    }

    public void healingPool(int gameStage) {
        if (gamePanel.keyHandler.enterPressed) {
            gamePanel.gameState = gameStage;
            gamePanel.ui.currentDialogue = "You drink the water.\n Your life has been recovered.";
            gamePanel.player.life = gamePanel.player.maxLife;
        }
    }
}
