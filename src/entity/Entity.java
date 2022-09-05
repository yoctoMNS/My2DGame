package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Entity {
    public GamePanel gamePanel;
    public int worldX;
    public int worldY;
    public int speed;
    public BufferedImage up1, up2;
    public BufferedImage down1, down2;
    public BufferedImage left1, left2;
    public BufferedImage right1, right2;
    public BufferedImage attackUp1, attackUp2;
    public BufferedImage attackDown1, attackDown2;
    public BufferedImage attackLeft1, attackLeft2;
    public BufferedImage attackRight1, attackRight2;
    public String direction = "down";
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public int actionLockCounter = 0;
    String dialogues[] = new String[20];
    int dialogueIndex = 0;
    public BufferedImage image;
    public BufferedImage image2;
    public BufferedImage image3;
    public String name;
    public boolean collision = false;
    public boolean invincible = false;
    public int invincibleCounter = 0;
    public int type; // 0 = player, 1 = npc, 2 = monster
    boolean attacking = false;
    Rectangle attackArea = new Rectangle();

    // CHARACTER STATUS
    public int maxLife;
    public int life;

    public Entity(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setAction() {
    }

    public void setSpeak() {
    }

    public void update() {
        setAction();

        collisionOn = false;
        gamePanel.collisionChecker.checkTile(this);
        gamePanel.collisionChecker.checkOjbect(this, false);
        gamePanel.collisionChecker.checkEntity(this, gamePanel.npc);
        gamePanel.collisionChecker.checkEntity(this, gamePanel.monster);
        boolean contactPlayer = gamePanel.collisionChecker.checkPlayer(this);

        if (type == 2 && contactPlayer == true) {
            if (gamePanel.player.invincible == false) {
                // we can give damage
                gamePanel.player.life -= 1;
                gamePanel.player.invincible = true;
            }
        }

        // IF COLLISION IS FALSE, PLAYER CAN MOVE
        if (!collisionOn) {
            switch (direction) {
            case "up": worldY -= speed; break;
            case "down": worldY += speed; break;
            case "left": worldX -= speed; break;
            case "right": worldX += speed; break;
            }
        }

        spriteCounter++;

        if (spriteCounter > 12) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }

            spriteCounter = 0;
        }

        if (invincible == true) {
            invincibleCounter++;

            if (invincibleCounter > 40) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    public void draw(Graphics2D g) {
        BufferedImage image = null;
        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        switch (direction) {
        case "up":
            if (spriteNum == 1) {
                image = up1;
            }
            if (spriteNum == 2) {
                image = up2;
            }
            break;
        case "down":
            if (spriteNum == 1) {
                image = down1;
            }
            if (spriteNum == 2) {
                image = down2;
            }
            break;
        case "left":
            if (spriteNum == 1) {
                image = left1;
            }
            if (spriteNum == 2) {
                image = left2;
            }
            break;
        case "right":
            if (spriteNum == 1) {
                image = right1;
            }
            if (spriteNum == 2) {
                image = right2;
            }
            break;
        }

        if (invincible == true) {
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4F));
        }

        g.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);

        // Reset alpha
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));
    }

    public BufferedImage setup(String imagePath, int width, int height) {
        UtilityTool tool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = tool.scaleImage(image, width, height);
        } catch (IOException e) {
            System.err.println("プレイヤーの画像を読み込む際にエラーが発生しました。");
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.err.println("指定されたパスの画像を見つけることが出来ませんでした。");
            e.printStackTrace();
        }

        return image;
    }

    public void speak() {
        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }

        gamePanel.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch (gamePanel.player.direction) {
        case "up":
            direction = "down";
            break;
        case "down":
            direction = "up";
            break;
        case "left":
            direction = "right";
            break;
        case "right":
            direction = "left";
            break;
        }
    }
}
