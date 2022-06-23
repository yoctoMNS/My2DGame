package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Chest extends SuperObject {
    public OBJ_Chest() {
        this.name = "Chest";
        try {
            this.image = ImageIO.read(OBJ_Key.class.getResourceAsStream("/objects/chest.png"));
        } catch (IOException e) {
            System.err.println("チェストの画像の読み込みに失敗しました。");
            e.printStackTrace();
        }
    }
}
