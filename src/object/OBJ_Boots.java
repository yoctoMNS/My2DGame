package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Boots extends SuperObject {
    GamePanel gamePanel;

    public OBJ_Boots(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.name = "Boots";
        try {
            this.image = ImageIO.read(OBJ_Key.class.getResourceAsStream("/objects/boots.png"));
            tool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
        } catch (IOException e) {
            System.err.println("靴の画像の読み込みに失敗しました。");
            e.printStackTrace();
        }
        this.collision = true;
    }
}
