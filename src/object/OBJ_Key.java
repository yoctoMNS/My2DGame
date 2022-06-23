package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Key extends SuperObject {
    public OBJ_Key() {
        this.name = "Key";
        try {
            this.image = ImageIO.read(OBJ_Key.class.getResourceAsStream("/objects/key.png"));
        } catch (IOException e) {
            System.err.println("鍵の画像の読み込みに失敗しました。");
            e.printStackTrace();
        }
    }
}
