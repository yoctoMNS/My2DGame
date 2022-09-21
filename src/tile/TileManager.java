package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gamePanel;
    public Tile[] tile;
    public int[][] mapTileNum;

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tile = new Tile[50];
        mapTileNum = new int[gamePanel.MAX_WORLD_COL][gamePanel.MAX_WORLD_ROW];

        getTileImage();
        loadMap("/maps/worldV2.txt");
    }

    private void getTileImage() {
        // PLACE HOLDER
        setup(0, "grass00", false);
        setup(1, "grass00", false);
        setup(2, "grass00", false);
        setup(3, "grass00", false);
        setup(4, "grass00", false);
        setup(5, "grass00", false);
        setup(6, "grass00", false);
        setup(7, "grass00", false);
        setup(8, "grass00", false);
        setup(9, "grass00", false);
        setup(10, "grass00", false);
        // PLACE HOLDER

        setup(11, "grass01", false);
        setup(12, "water00", true);
        setup(13, "water01", true);
        setup(14, "water02", true);
        setup(15, "water03", true);
        setup(16, "water04", true);
        setup(17, "water05", true);
        setup(18, "water06", true);
        setup(19, "water07", true);
        setup(20, "water08", true);
        setup(21, "water09", true);
        setup(22, "water10", true);
        setup(23, "water11", true);
        setup(24, "water12", true);
        setup(25, "water13", true);
        setup(26, "road00", false);
        setup(27, "road01", false);
        setup(28, "road02", false);
        setup(29, "road03", false);
        setup(30, "road04", false);
        setup(31, "road05", false);
        setup(32, "road06", false);
        setup(33, "road07", false);
        setup(34, "road08", false);
        setup(35, "road09", false);
        setup(36, "road10", false);
        setup(37, "road11", false);
        setup(38, "road12", false);
        setup(39, "earth", false);
        setup(40, "wall", true);
        setup(41, "tree", true);
    }

    public void setup(int index, String imageName, boolean collision) {
        UtilityTool tool = new UtilityTool();

        tile[index] = new Tile();
        try {
            tile[index].image = ImageIO.read(TileManager.class.getResourceAsStream("/tiles/" + imageName + ".png"));
            tile[index].image = tool.scaleImage(tile[index].image, gamePanel.tileSize, gamePanel.tileSize);
            tile[index].collision = collision;
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
            int worldX = worldCol * gamePanel.tileSize;
            int worldY = worldRow * gamePanel.tileSize;
            int screenX = worldX - (int)gamePanel.player.worldX + gamePanel.player.screenX;
            int screenY = worldY - (int)gamePanel.player.worldY + gamePanel.player.screenY;

            if (worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX &&
                worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
                worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
                worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY) {
                g.drawImage(tile[tileNum].image, screenX, screenY, null);
            }
            worldCol++;

            if (worldCol == gamePanel.MAX_WORLD_COL) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
