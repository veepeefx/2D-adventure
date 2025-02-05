package Main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // screen settings
    final int tileSize = 16 * 4;
    final int screenColumns = 16;
    final int screenRows = 12;

    final int FPS = 60;

    Thread gameThread;

    Keylogger keylogger = new Keylogger();

    int posX = 100;
    int posY = 100;

    int stepLength = 4;

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

        // origo (0,0) is in the top left corner
        if (keylogger.upPressed){
            posY -= stepLength;
        }
        if (keylogger.downPressed){
            posY += stepLength;
        }
        if (keylogger.leftPressed){
            posX -= stepLength;
        }
        if (keylogger.rightPressed){
            posX += stepLength;
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // testing tile
        g2.setColor(Color.WHITE);
        g2.fillRect(posX, posY, tileSize, tileSize);
    }
}
