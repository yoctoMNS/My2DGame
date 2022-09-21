package main;

import entity.Entity;
import entity.Player;
import tile.TileManager;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable {
    // SCREEN SETTINGS
    final int ORIGINAL_TILE_SIZE = 16; // 16 x 16 tile
    final int SCALE = 3;

    public int tileSize = ORIGINAL_TILE_SIZE * SCALE; // 48 x 48
    public int maxScreenCol = 16;
    public int maxScreenRow = 12;
    public int screenWidth = tileSize * maxScreenCol; // 768 px
    public int screenHeight = tileSize * maxScreenRow; // 576 px

    // WORLD SETTINGS
    public final int MAX_WORLD_COL = 50;
    public final int MAX_WORLD_ROW = 50;

    // FPS
    final int FPS = 60;

    // SYSTEM
    TileManager tileManager = new TileManager(this);
    public KeyHandler keyHandler = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eventHandler = new EventHandler(this);
    Thread gameThread;

    // ENTITY AND OBJECT
    public Player player = new Player(this, keyHandler);
    public Entity[] obj = new Entity[10];
    public Entity[] npc = new Entity[10];
    public Entity[] monster = new Entity[20];
    ArrayList<Entity> entityList = new ArrayList<>();

    // GAME STATE
    public int gameState;
    public final int TITLE_STATE = 0;
    public final int PLAY_STATE = 1;
    public final int PAUSE_STATE = 2;
    public final int DIALOG_STATAE = 3;

    public GamePanel() {
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        addKeyListener(keyHandler);
        setFocusable(true);
    }

    public void setupGame() {
        assetSetter.setObject();
        assetSetter.setNPC();
        assetSetter.setMonster();
        gameState = TITLE_STATE;
    }

    public void zoomInOut(int i) {
        int oldWorldWidth = tileSize * MAX_WORLD_COL;
        tileSize += i;
        int newWorldwidth = tileSize * MAX_WORLD_COL;
        player.speed = newWorldwidth / 600;
        int multiplier = newWorldwidth / oldWorldWidth;
        int newPlayerWorldX = player.worldX * multiplier;
        int newPlayerWorldY = player.worldY * multiplier;
        player.worldX = newPlayerWorldX;
        player.worldY = newPlayerWorldY;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime = 0;
        long timer = 0;
        long drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += currentTime - lastTime;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();

                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        if (gameState == PLAY_STATE) {
            // PLAYER
            player.update();
            // NPC
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    npc[i].update();
                }
            }
            // MONSTER
            for (int i = 0; i < monster.length; i++) {
                if (monster[i] != null) {
                    if (monster[i].alive == true && monster[i].dying == false) {
                        monster[i].update();
                    }
                    if (monster[i].alive == false) {
                        monster[i] = null;
                    }
                }
            }
        }

        if (gameState == PAUSE_STATE) {
            // nothing
        }
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics;

        // DEBUG
        long drawStart = 0;
        if (keyHandler.checkDrawTime) {
            drawStart = System.nanoTime();
        }

        // TITLE SCREEN
        if (gameState == TITLE_STATE) {
            ui.draw(g);
        }
        // OTHERS
        else {
            // TILE
            tileManager.draw(g);

            // ADD ENTITIES TO THE LIST
            entityList.add(player);
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    entityList.add(npc[i]);
                }
            }

            for (int i = 0; i < obj.length; i++) {
                if (obj[i] != null) {
                    entityList.add(obj[i]);
                }
            }

            for (int i = 0; i < monster.length; i++) {
                if (monster[i] != null) {
                    entityList.add(monster[i]);
                }
            }

            // SORT
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity o1, Entity o2) {
                    return Integer.compare(o1.worldY, o2.worldY);
                }
            });

            // DRAW ENTITIES
            for (int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g);
            }

            // EMPTY ENTITY LIST
            entityList.clear();

            // UI
            ui.draw(g);
        }

        // DEBUG
        if (keyHandler.checkDrawTime) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g.setColor(Color.WHITE);
            g.drawString("Draw Time : " + passed, 10, 400);
//            System.out.println("Draw Time" + passed);
        }

        g.dispose();
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSE(int i) {
        se.setFile(i);
        se.play();
    }
}
