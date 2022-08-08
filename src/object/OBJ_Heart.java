package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Heart extends  SuperObject {
    GamePanel gamePanel;

    public OBJ_Heart(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.name = "Heart";

        try {
            this.image  = ImageIO.read(OBJ_Key.class.getResourceAsStream("/objects/heart_full.png" ));
            this.image2 = ImageIO.read(OBJ_Key.class.getResourceAsStream("/objects/heart_half.png" ));
            this.image3 = ImageIO.read(OBJ_Key.class.getResourceAsStream("/objects/heart_blank.png"));
            this.image  = tool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize );
            this.image2 = tool.scaleImage(image2, gamePanel.tileSize, gamePanel.tileSize);
            this.image3 = tool.scaleImage(image3, gamePanel.tileSize, gamePanel.tileSize);
        } catch (IOException e) {
            System.err.println("鍵の画像の読み込みに失敗しました。");
            e.printStackTrace();
        }
    }
}
