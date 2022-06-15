package main;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;

public class GamePanel extends JPanel implements Runnable {
    // SCREEN SETTINGS
    final int ORIGINAL_TILE_SIZE = 16; // 16 x 16 tile
    final int SCALE = 3;

    final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE; // 48 x 48
    final int MAX_SCREEN_COL = 16;
    final int MAX_SCREEN_ROW = 12;
    final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL; // 768 px
    final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW; // 576 px

    Thread gameThread;

    public GamePanel() {
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
    }
}
