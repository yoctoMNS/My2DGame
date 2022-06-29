package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Chest extends SuperObject {
    GamePanel gamePanel;

    public OBJ_Chest(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.name = "Chest";
        try {
            this.image = ImageIO.read(OBJ_Key.class.getResourceAsStream("/objects/chest.png"));
            tool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
        } catch (IOException e) {
            System.err.println("チェストの画像の読み込みに失敗しました。");
            e.printStackTrace();
        }
    }
}
