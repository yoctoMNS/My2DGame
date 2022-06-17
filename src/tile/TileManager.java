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
        mapTileNum = new int[gamePanel.MAX_SCREEN_COL][gamePanel.MAX_SCREEN_ROW];

        getTileImage();
        loadMap("/maps/map01.txt");
    }

    private void getTileImage() {
        tile[0] = new Tile();
        tile[1] = new Tile();
        tile[2] = new Tile();
        try {
            tile[0].image = ImageIO.read(TileManager.class.getResourceAsStream("/tiles/grass.png"));
            tile[1].image = ImageIO.read(TileManager.class.getResourceAsStream("/tiles/wall.png"));
            tile[2].image = ImageIO.read(TileManager.class.getResourceAsStream("/tiles/water.png"));
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

                while (col < gamePanel.MAX_SCREEN_COL && row < gamePanel.MAX_SCREEN_ROW) {
                    String line = bufferedReader.readLine();

                    while (col < gamePanel.MAX_SCREEN_COL) {
                        String[] numbers = line.split(" ");
                        int num = Integer.parseInt(numbers[col]);
                        mapTileNum[col][row] = num;
                        col++;
                    }

                    if (col == gamePanel.MAX_SCREEN_COL) {
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
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while (col < gamePanel.MAX_SCREEN_COL && row < gamePanel.MAX_SCREEN_ROW) {
            int tileNum = mapTileNum[col][row];

            g.drawImage(tile[tileNum].image, x, y, gamePanel.TILE_SIZE, gamePanel.TILE_SIZE, null);
            col++;
            x += gamePanel.TILE_SIZE;

            if (col == gamePanel.MAX_SCREEN_COL) {
                col = 0;
                x = 0;
                row++;
                y += gamePanel.TILE_SIZE;
            }
        }
    }
}
