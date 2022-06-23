package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Door extends SuperObject {
    public OBJ_Door() {
        this.name = "Door";
        try {
            this.image = ImageIO.read(OBJ_Key.class.getResourceAsStream("/objects/door.png"));
        } catch (IOException e) {
            System.err.println("ドアの画像の読み込みに失敗しました。");
            e.printStackTrace();
        }
        this.collision = true;
    }
}
