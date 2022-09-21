package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
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
    public boolean alive = true;
    public boolean dying = false;
    int dyingCounter = 0;
    public boolean hpBarOn = false;
    public int hpBarCounter = 0;

    // CHARACTER STATUS
    public int maxLife;
    public int life;

    public Entity(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setAction() {
    }

    public void damageRection() {
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
                gamePanel.playSE(6);
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

        // Monster HP bar
        if (type == 2 && hpBarOn == true) {
            double oneScale = (double) gamePanel.tileSize / maxLife;
            double hpBarValue = oneScale * life;

            g.setColor(new Color(35, 35, 35));
            g.fillRect(screenX - 1, screenY - 16, gamePanel.tileSize + 2, 12);

            g.setColor(new Color(255, 0, 30));
            g.fillRect(screenX, screenY - 15, (int)hpBarValue, 10);

            hpBarCounter++;
            if (hpBarCounter > 600) {
                hpBarCounter = 0;
                hpBarOn = false;
            }
        }

        if (invincible == true) {
            hpBarOn = true;
            changeAlpha(g, 0.4F);
        }

        if (dying == true) {
            dyingAnimation(g);
        }

        g.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);

        // Reset alpha
        changeAlpha(g, 1F);
    }

    private void dyingAnimation(Graphics2D g) {
        dyingCounter++;
        int i = 10;

        if (dyingCounter <= i) {
            changeAlpha(g, 0);
        }
        if (dyingCounter > i && dyingCounter <= i * 2) {
            changeAlpha(g, 1);
        }
        if (dyingCounter > i * 2 && dyingCounter <= i * 3) {
            changeAlpha(g, 0);
        }
        if (dyingCounter > i * 3 && dyingCounter <= i * 4) {
            changeAlpha(g, 1);
        }
        if (dyingCounter > i * 4 && dyingCounter <= i * 5) {
            changeAlpha(g, 0);
        }
        if (dyingCounter > i * 5 && dyingCounter <= i * 6) {
            changeAlpha(g, 1);
        }
        if (dyingCounter > i * 6 && dyingCounter <= i * 7) {
            changeAlpha(g, 0);
        }
        if (dyingCounter > i * 7 && dyingCounter <= i * 8) {
            changeAlpha(g, 1);
        }
        if (dyingCounter > i * 8) {
            dying = false;
            alive = false;
        }
    }

    private void changeAlpha(Graphics2D g, float alpha) {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
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
