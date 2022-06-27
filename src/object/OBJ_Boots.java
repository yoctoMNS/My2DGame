package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Boots extends SuperObject {
    public OBJ_Boots() {
        this.name = "Boots";
        try {
            this.image = ImageIO.read(OBJ_Key.class.getResourceAsStream("/objects/boots.png"));
        } catch (IOException e) {
            System.err.println("靴の画像の読み込みに失敗しました。");
            e.printStackTrace();
        }
        this.collision = true;
    }
}
