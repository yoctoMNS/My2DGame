package entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Player extends Entity {
    KeyHandler keyHandler;
    public final int screenX;
    public final int screenY;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        super(gamePanel);

        this.keyHandler = keyHandler;
        this.screenX = gamePanel.screenWidth / 2 - (gamePanel.tileSize / 2);
        this.screenY = gamePanel.screenHeight / 2 - (gamePanel.tileSize / 2);
        this.solidArea = new Rectangle();
        this.solidArea.x = 8;
        this.solidArea.y = 16;
        this.solidAreaDefaultX = this.solidArea.x;
        this.solidAreaDefaultY = this.solidArea.y;
        this.solidArea.width = 32;
        this.solidArea.height = 32;
        this.attackArea.width = 36;
        this.attackArea.height = 36;

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
    }

    public void setDefaultValues() {
        worldX = gamePanel.tileSize * 23;
        worldY = gamePanel.tileSize * 21;
        speed = 4;
        direction = "down";

        // PLAYER STATUS
        maxLife = 6;
        life = maxLife;
    }

    public void getPlayerImage() {
        up1    = setup("/player/boy_up_1", gamePanel.tileSize, gamePanel.tileSize);
        up2    = setup("/player/boy_up_2", gamePanel.tileSize, gamePanel.tileSize);
        down1  = setup("/player/boy_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2  = setup("/player/boy_down_2", gamePanel.tileSize, gamePanel.tileSize);
        left1  = setup("/player/boy_left_1", gamePanel.tileSize, gamePanel.tileSize);
        left2  = setup("/player/boy_left_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/player/boy_right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/player/boy_right_2", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void getPlayerAttackImage() {
        attackUp1    = setup("/player/boy_attack_up_1", gamePanel.tileSize, gamePanel.tileSize * 2);
        attackUp2    = setup("/player/boy_attack_up_2", gamePanel.tileSize, gamePanel.tileSize * 2);
        attackDown1  = setup("/player/boy_attack_down_1", gamePanel.tileSize, gamePanel.tileSize * 2);
        attackDown2  = setup("/player/boy_attack_down_2", gamePanel.tileSize, gamePanel.tileSize * 2);
        attackLeft1  = setup("/player/boy_attack_left_1", gamePanel.tileSize * 2, gamePanel.tileSize);
        attackLeft2  = setup("/player/boy_attack_left_2", gamePanel.tileSize * 2, gamePanel.tileSize);
        attackRight1 = setup("/player/boy_attack_right_1", gamePanel.tileSize * 2, gamePanel.tileSize);
        attackRight2 = setup("/player/boy_attack_right_2", gamePanel.tileSize * 2, gamePanel.tileSize);
    }

    public void update() {
        if (attacking) {
            attacking();
        } else if (keyHandler.upPressed ||
                   keyHandler.downPressed ||
                   keyHandler.leftPressed ||
                   keyHandler.rightPressed ||
                   keyHandler.enterPressed) {
            if (keyHandler.upPressed) {
                direction = "up";
            } else if (keyHandler.downPressed) {
                direction = "down";
            } else if (keyHandler.leftPressed) {
                direction = "left";
            } else if (keyHandler.rightPressed) {
                direction = "right";
            }

            // CHECK TILE COLLISION
            collisionOn = false;
            gamePanel.collisionChecker.checkTile(this);

            // CHECK OBJECT COLLISION
            int objIndex = gamePanel.collisionChecker.checkOjbect(this, true);
            pickUpObject(objIndex);

            // CHECK NPC COLLISION
            int npcIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.npc);
            interactNPC(npcIndex);

            // CHECK MONSTER COLLISION
            int monsterIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.monster);
            contactMonster(monsterIndex);

            // CHECK EVENT
            gamePanel.eventHandler.checkEvent();

            // IF COLLISION IS FALSE, PLAYER CAN MOVE
            if (!collisionOn && !keyHandler.enterPressed) {
                switch (direction) {
                case "up": worldY -= speed; break;
                case "down": worldY += speed; break;
                case "left": worldX -= speed; break;
                case "right": worldX += speed; break;
                }
            }

            gamePanel.keyHandler.enterPressed = false;

            spriteCounter++;

            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }

                spriteCounter = 0;
            }
        }

        // This needs to be outside of key if statement.
        if (invincible == true) {
            invincibleCounter++;

            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    private void attacking() {
        spriteCounter++;

        if (spriteCounter <= 5) {
            spriteNum = 1;
        }
        if (spriteCounter > 5 && spriteCounter <= 25) {
            spriteNum = 2;

            // Save the current worldX, worldY, solidArea
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            // Adjust player's worldX/Y for the attackArea
            switch (direction) {
            case "up":
                worldY -= attackArea.height;
                break;
            case "down":
                worldY += attackArea.height;
                break;
            case "left":
                worldX -= attackArea.width;
                break;
            case "right":
                worldX += attackArea.width;
                break;
            }

            // attackArea becomes solidArea
            solidArea.width = attackArea.width;;
            solidArea.height = attackArea.height;

            // Check monster collision with the updated worldX, worldY and solidArea
            int monsterIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.monster);
            damageMonster(monsterIndex);

            // After checking collision, restore the original data
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        if (spriteCounter > 25) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    private void damageMonster(int i) {
        if (i != 999) {
            if (!gamePanel.monster[i].invincible) {
                gamePanel.monster[i].life -= 1;
                gamePanel.monster[i].invincible = true;

                if (gamePanel.monster[i].life <= 0) {
                    gamePanel.monster[i] = null;
                }
            }
        }
    }

    private void contactMonster(int monsterIndex) {
        if (monsterIndex != 999) {
            if (invincible == false) {
                life -= 1;
                invincible = true;
            }
        }
    }

    public void pickUpObject(int i) {
        if (i != 999) {
        }
    }

    private void interactNPC(int i) {
        if (gamePanel.keyHandler.enterPressed) {
            if (i != 999) {
                gamePanel.gameState = gamePanel.DIALOG_STATAE;
                gamePanel.npc[i].speak();
            } else {
                attacking = true;
            }
        }
    }

    public void draw(Graphics2D g) {
        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch (direction) {
        case "up":
            if (attacking) {
                tempScreenY = screenY - gamePanel.tileSize;

                if (spriteNum == 1) {
                    image = attackUp1;
                }
                if (spriteNum == 2) {
                    image = attackUp2;
                }
            } else {
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
            }
            break;
        case "down":
            if (attacking) {
                if (spriteNum == 1) {
                    image = attackDown1;
                }
                if (spriteNum == 2) {
                    image = attackDown2;
                }
            } else {
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
            }
            break;
        case "left":
            if (attacking) {
                tempScreenX = screenX - gamePanel.tileSize;

                if (spriteNum == 1) {
                    image = attackLeft1;
                }
                if (spriteNum == 2) {
                    image = attackLeft2;
                }
            } else {
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
            }
            break;
        case "right":
            if (attacking) {
                if (spriteNum == 1) {
                    image = attackRight1;
                }
                if (spriteNum == 2) {
                    image = attackRight2;
                }
            } else {
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
            }
            break;
        }

        if (invincible == true) {
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4F));
        }

        g.drawImage(image, tempScreenX, tempScreenY, null);

        // Reset alpha
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));
    }
}
