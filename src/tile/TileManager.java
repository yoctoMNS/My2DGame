package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gamePanel;
    Tile[] tile;
    int[][] mapTileNum;

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tile = new Tile[10];
        mapTileNum = new int[gamePanel.MAX_WORLD_COL][gamePanel.MAX_WORLD_ROW];

        getTileImage();
        loadMap("/maps/world01.txt");
    }

    private void getTileImage() {
        tile[0] = new Tile();
        tile[1] = new Tile();
        tile[2] = new Tile();
        tile[3] = new Tile();
        tile[4] = new Tile();
        tile[5] = new Tile();
        try {
            tile[0].image = ImageIO.read(TileManager.class.getResourceAsStream("/tiles/grass.png"));
            tile[1].image = ImageIO.read(TileManager.class.getResourceAsStream("/tiles/wall.png"));
            tile[2].image = ImageIO.read(TileManager.class.getResourceAsStream("/tiles/water.png"));
            tile[3].image = ImageIO.read(TileManager.class.getResourceAsStream("/tiles/earth.png"));
            tile[4].image = ImageIO.read(TileManager.class.getResourceAsStream("/tiles/tree.png"));
            tile[5].image = ImageIO.read(TileManager.class.getResourceAsStream("/tiles/sand.png"));
        } catch (IOException e) {
            System.err.println("タイル画像の読み込みに失敗しました。");
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.err.println("指定されたパスに画像が存在しません。");
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath) {
            try {
                InputStream inputStream = TileManager.class.getResourceAsStream(filePath);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                int col = 0;
                int row = 0;

                while (col < gamePanel.MAX_WORLD_COL && row < gamePanel.MAX_WORLD_ROW) {
                    String line = bufferedReader.readLine();

                    while (col < gamePanel.MAX_WORLD_COL) {
                        String[] numbers = line.split(" ");
                        int num = Integer.parseInt(numbers[col]);
                        mapTileNum[col][row] = num;
                        col++;
                    }

                    if (col == gamePanel.MAX_WORLD_COL) {
                        col = 0;
                        row++;
                    }
                }

                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    public void draw(Graphics2D g) {
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gamePanel.MAX_WORLD_COL && worldRow < gamePanel.MAX_WORLD_ROW) {
            int tileNum = mapTileNum[worldCol][worldRow];
            int worldX = worldCol * gamePanel.TILE_SIZE;
            int worldY = worldRow * gamePanel.TILE_SIZE;
            int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
            int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

            if (worldX + gamePanel.TILE_SIZE > gamePanel.player.worldX - gamePanel.player.screenX &&
                worldX - gamePanel.TILE_SIZE < gamePanel.player.worldX + gamePanel.player.screenX &&
                worldY + gamePanel.TILE_SIZE > gamePanel.player.worldY - gamePanel.player.screenY &&
                worldY - gamePanel.TILE_SIZE < gamePanel.player.worldY + gamePanel.player.screenY) {
                g.drawImage(tile[tileNum].image, screenX, screenY, gamePanel.TILE_SIZE, gamePanel.TILE_SIZE, null);
            }
            worldCol++;

            if (worldCol == gamePanel.MAX_WORLD_COL) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
