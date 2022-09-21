package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Key extends Entity {
    public OBJ_Key(GamePanel gamePanel) {
        super(gamePanel);

        this.name = "Key";
        this.down1 = setup("/objects/key", gamePanel.tileSize, gamePanel.tileSize);
    }
}
