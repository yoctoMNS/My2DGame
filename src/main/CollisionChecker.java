package main;

import entity.Entity;

public class CollisionChecker {
    GamePanel gamePanel;

    public CollisionChecker(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void checkTile(Entity entity) {
        int entityLeftWorldX = (int)entity.worldX + entity.solidArea.x;
        int entityRightWorldX = (int)entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = (int)entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = (int)entity.worldY + entity.solidArea.y + entity.solidArea.height;
        int entityLeftCol = entityLeftWorldX / gamePanel.tileSize;
        int entityRightCol = entityRightWorldX / gamePanel.tileSize;
        int entityTopRow = entityTopWorldY / gamePanel.tileSize;
        int entityBottomRow = entityBottomWorldY / gamePanel.tileSize;
        int tileNum1, tileNum2;

        switch (entity.direction) {
        case "up":
            entityTopRow = (entityTopWorldY - (int)entity.speed) / gamePanel.tileSize;
            tileNum1 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityTopRow];
            tileNum2 = gamePanel.tileManager.mapTileNum[entityRightCol][entityTopRow];

            if (gamePanel.tileManager.tile[tileNum1].collision || gamePanel.tileManager.tile[tileNum2].collision) {
                entity.collisionOn = true;
            }
            break;
        case "down":
            entityBottomRow = (entityBottomWorldY + (int)entity.speed) / gamePanel.tileSize;
            tileNum1 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
            tileNum2 = gamePanel.tileManager.mapTileNum[entityRightCol][entityBottomRow];

            if (gamePanel.tileManager.tile[tileNum1].collision || gamePanel.tileManager.tile[tileNum2].collision) {
                entity.collisionOn = true;
            }
            break;
        case "left":
            entityLeftCol = (entityLeftWorldX - (int)entity.speed) / gamePanel.tileSize;
            tileNum1 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityTopRow];
            tileNum2 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityBottomRow];

            if (gamePanel.tileManager.tile[tileNum1].collision || gamePanel.tileManager.tile[tileNum2].collision) {
                entity.collisionOn = true;
            }
            break;
        case "right":
            entityRightCol = (entityRightWorldX + (int)entity.speed) / gamePanel.tileSize;
            tileNum1 = gamePanel.tileManager.mapTileNum[entityRightCol][entityTopRow];
            tileNum2 = gamePanel.tileManager.mapTileNum[entityRightCol][entityBottomRow];

            if (gamePanel.tileManager.tile[tileNum1].collision || gamePanel.tileManager.tile[tileNum2].collision) {
                entity.collisionOn = true;
            }
            break;
        }
    }
}
