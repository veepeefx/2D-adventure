package Main;

import Entities.Player;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // screen settings
    public final int tileSize = 16 * 4;
    public final int screenColumns = 16;
    public final int screenRows = 12;

    public final int worldColumnLimit = 50;
    public final int worldRowLimit = 50;

    final int FPS = 60;

    Thread gameThread;

    Keylogger keylogger = new Keylogger();
    Player player = new Player(this, keylogger);
    TileManagement tileM = new TileManagement(this);

    GamePanel(){
        this.setPreferredSize(new Dimension(tileSize * screenColumns,
                                             tileSize * screenRows));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keylogger);
        this.setFocusable(true);

        start_gameThread();
    }

    void start_gameThread(){

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        long frameLenght = 1000000000 / FPS; // 1s / FPS
        long nextFrame = System.nanoTime() + frameLenght;

        while (gameThread != null){

            update();
            // calls paintComponent
            repaint();

            // timer to sleep till nexFrame is supposed to be shown
            try {
                long timeLeft = nextFrame - System.nanoTime();
                timeLeft = timeLeft / 1000000; // ms

                if (timeLeft < 0){
                    timeLeft = 0;
                }

                Thread.sleep(timeLeft);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
            nextFrame = System.nanoTime() + frameLenght;
        }
    }

    void update() {

        player.movement();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // testing tile
        tileM.draw(g2);
        player.draw(g2);
    }
}
