package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Door extends SuperObject {
    GamePanel gamePanel;

    public OBJ_Door(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.name = "Door";
        try {
            this.image = ImageIO.read(OBJ_Key.class.getResourceAsStream("/objects/door.png"));
            tool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
        } catch (IOException e) {
            System.err.println("ドアの画像の読み込みに失敗しました。");
            e.printStackTrace();
        }
        this.collision = true;
    }
}
