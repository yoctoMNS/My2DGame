package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Heart extends Entity {
    public OBJ_Heart(GamePanel gamePanel) {
        super(gamePanel);

        this.name = "Heart";
        this.image  = setup("/objects/heart_full", gamePanel.tileSize, gamePanel.tileSize);
        this.image2 = setup("/objects/heart_half", gamePanel.tileSize, gamePanel.tileSize);
        this.image3 = setup("/objects/heart_blank", gamePanel.tileSize, gamePanel.tileSize);
    }
}
