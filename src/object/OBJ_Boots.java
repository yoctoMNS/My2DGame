package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Boots extends Entity {
    public OBJ_Boots(GamePanel gamePanel) {
        super(gamePanel);

        this.name = "Boots";
        this.down1 = setup("objects/boots");
        this.collision = true;
    }
}
