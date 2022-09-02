package main;

public class EventHandler {
    GamePanel gamePanel;
    EventRect[][] eventRect;
    int previousEventX;
    int previousEventY;
    boolean canTouchEvent;

    public EventHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.eventRect = new EventRect[gamePanel.MAX_WORLD_COL][gamePanel.MAX_WORLD_ROW];
        this.canTouchEvent = true;

        int col = 0;
        int row = 0;

        while (col < gamePanel.MAX_WORLD_COL && row < gamePanel.MAX_WORLD_ROW) {
            eventRect[col][row] = new EventRect();
            eventRect[col][row].x = 23;
            eventRect[col][row].y = 23;
            eventRect[col][row].width = 2;
            eventRect[col][row].height = 2;
            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;

            col++;
            if (col == gamePanel.MAX_WORLD_COL) {
                col = 0;
                row++;
            }
        }

    }

    public void checkEvent() {
        // Check if the player character is more than 1 tile away from the last event.
        int xDistance = Math.abs(gamePanel.player.worldX - previousEventX);
        int yDistance = Math.abs(gamePanel.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);

        if (distance > gamePanel.tileSize) {
            canTouchEvent = true;
        }

        if (canTouchEvent) {
            if (hit(27, 16, "right")) damagePit(27, 16, gamePanel.DIALOG_STATAE);
            if (hit(23, 19, "any")) damagePit(23, 19, gamePanel.DIALOG_STATAE);
            if (hit(23, 12, "up")) healingPool(23, 12, gamePanel.DIALOG_STATAE);
        }
    }

    private void teleport(int gameState) {
        gamePanel.gameState = gameState;
        gamePanel.ui.currentDialogue = "Teleport!";
        gamePanel.player.worldX = gamePanel.tileSize * 37;
        gamePanel.player.worldY = gamePanel.tileSize * 10;
    }

    private void damagePit(int col, int row, int gameState) {
        gamePanel.gameState = gameState;
        gamePanel.ui.currentDialogue = "You fall into a pit!";
        gamePanel.player.life--;
        // eventRect[col][row].eventDone = true;
        canTouchEvent = false;
    }

    public boolean hit(int col, int row, String reqDirection) {
        boolean hit = false;

        gamePanel.player.solidArea.x = gamePanel.player.worldX + gamePanel.player.solidArea.x;
        gamePanel.player.solidArea.y = gamePanel.player.worldY + gamePanel.player.solidArea.y;
        eventRect[col][row].x = col * gamePanel.tileSize + eventRect[col][row].x;
        eventRect[col][row].y = row * gamePanel.tileSize + eventRect[col][row].y;

        if (gamePanel.player.solidArea.intersects(eventRect[col][row]) && !eventRect[col][row].eventDone) {
            if (gamePanel.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                hit = true;

                previousEventX = gamePanel.player.worldX;
                previousEventY = gamePanel.player.worldY;
            }
        }

        gamePanel.player.solidArea.x = gamePanel.player.solidAreaDefaultX;
        gamePanel.player.solidArea.y = gamePanel.player.solidAreaDefaultY;
        eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;

        return hit;
    }

    public void healingPool(int col, int row, int gameStage) {
        if (gamePanel.keyHandler.enterPressed) {
            gamePanel.gameState = gameStage;
            gamePanel.ui.currentDialogue = "You drink the water.\n Your life has been recovered.";
            gamePanel.player.life = gamePanel.player.maxLife;
        }
    }
}
