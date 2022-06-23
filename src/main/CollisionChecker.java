package main;

import entity.Entity;

public class CollisionChecker {
    GamePanel gamePanel;

    public CollisionChecker(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;
        int entityLeftCol = entityLeftWorldX / gamePanel.tileSize;
        int entityRightCol = entityRightWorldX / gamePanel.tileSize;
        int entityTopRow = entityTopWorldY / gamePanel.tileSize;
        int entityBottomRow = entityBottomWorldY / gamePanel.tileSize;
        int tileNum1, tileNum2;

        switch (entity.direction) {
        case "up":
            entityTopRow = (entityTopWorldY - entity.speed) / gamePanel.tileSize;
            tileNum1 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityTopRow];
            tileNum2 = gamePanel.tileManager.mapTileNum[entityRightCol][entityTopRow];

            if (gamePanel.tileManager.tile[tileNum1].collision || gamePanel.tileManager.tile[tileNum2].collision) {
                entity.collisionOn = true;
            }
            break;
        case "down":
            entityBottomRow = (entityBottomWorldY + entity.speed) / gamePanel.tileSize;
            tileNum1 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
            tileNum2 = gamePanel.tileManager.mapTileNum[entityRightCol][entityBottomRow];

            if (gamePanel.tileManager.tile[tileNum1].collision || gamePanel.tileManager.tile[tileNum2].collision) {
                entity.collisionOn = true;
            }
            break;
        case "left":
            entityLeftCol = (entityLeftWorldX - entity.speed) / gamePanel.tileSize;
            tileNum1 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityTopRow];
            tileNum2 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityBottomRow];

            if (gamePanel.tileManager.tile[tileNum1].collision || gamePanel.tileManager.tile[tileNum2].collision) {
                entity.collisionOn = true;
            }
            break;
        case "right":
            entityRightCol = (entityRightWorldX + entity.speed) / gamePanel.tileSize;
            tileNum1 = gamePanel.tileManager.mapTileNum[entityRightCol][entityTopRow];
            tileNum2 = gamePanel.tileManager.mapTileNum[entityRightCol][entityBottomRow];

            if (gamePanel.tileManager.tile[tileNum1].collision || gamePanel.tileManager.tile[tileNum2].collision) {
                entity.collisionOn = true;
            }
            break;
        }
    }

    public int checkOjbect(Entity entity, boolean player) {
        int index = 999;

        for (int i = 0; i < gamePanel.object.length; i++) {
            if (gamePanel.object[i] != null) {
                // Get entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                // Get the object's solid area position
                gamePanel.object[i].solidArea.x = gamePanel.object[i].worldX + gamePanel.object[i].solidArea.x;
                gamePanel.object[i].solidArea.y = gamePanel.object[i].worldY + gamePanel.object[i].solidArea.y;

                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        if (entity.solidArea.intersects(gamePanel.object[i].solidArea)) {
                            if (gamePanel.object[i].collision) entity.collisionOn = true;
                            if (player) index = i;
                        }
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        if (entity.solidArea.intersects(gamePanel.object[i].solidArea)) {
                            if (gamePanel.object[i].collision) entity.collisionOn = true;
                            if (player) index = i;
                        }
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(gamePanel.object[i].solidArea)) {
                            if (gamePanel.object[i].collision) entity.collisionOn = true;
                            if (player) index = i;
                        }
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(gamePanel.object[i].solidArea)) {
                            if (gamePanel.object[i].collision) entity.collisionOn = true;
                            if (player) index = i;
                        }
                        break;
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gamePanel.object[i].solidArea.x = gamePanel.object[i].solidAreaDefaultX;
                gamePanel.object[i].solidArea.y = gamePanel.object[i].solidAreaDefaultY;
            }
        }

        return index;
    }
}
