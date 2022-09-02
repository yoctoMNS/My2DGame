package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Heart extends Entity {
    public OBJ_Heart(GamePanel gamePanel) {
        super(gamePanel);

        this.name = "Heart";
        this.image  = setup("/objects/heart_full");
        this.image2 = setup("/objects/heart_half");
        this.image3 = setup("/objects/heart_blank");
    }
}
