package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Key extends SuperObject {
    GamePanel gamePanel;

    public OBJ_Key(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.name = "Key";
        try {
            this.image = ImageIO.read(OBJ_Key.class.getResourceAsStream("/objects/key.png"));
            tool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
        } catch (IOException e) {
            System.err.println("鍵の画像の読み込みに失敗しました。");
            e.printStackTrace();
        }
    }
}
