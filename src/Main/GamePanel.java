package Main;

import Entities.Player;
import GUI.MenuManagement;
import Tools.GameStatus;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // screen settings
    public final int tileSize = 16 * 4;
    public final int screenColumns = 16;
    public final int screenRows = 12;

    // world size
    public final int worldColumnLimit = 50;
    public final int worldRowLimit = 50;

    // target FPS
    final int FPS = 60;
    // game is started to be in menu
    public GameStatus currentStatus = GameStatus.menu;

    public boolean gameRunning = true;

    public Thread gameThread;
    public Keylogger keylogger = new Keylogger(this);
    Player player = new Player(this, keylogger);
    TileManagement tileM = new TileManagement(this);
    MenuManagement menuM = new MenuManagement(this);

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

        while (gameRunning) {

            update();
            // calls paintComponent
            repaint();

            // timer to sleep till nexFrame is supposed to be shown
            try {
                long timeLeft = nextFrame - System.nanoTime();
                timeLeft = timeLeft / 1000000; // ms

                if (timeLeft < 0) {
                    timeLeft = 0;
                }

                Thread.sleep(timeLeft);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
            nextFrame = System.nanoTime() + frameLenght;
        }

        // after loop has been broken game has ended
        System.exit(0);
    }

    void update() {

        // entities move only if currentStatus is play
        if (currentStatus == GameStatus.play) {
            player.movement();
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        if (currentStatus == GameStatus.menu){
            menuM.draw_main_menu(g2);
        }

        // game shows behind the pause screen so we need to update it even thought game
        // is paused
        if (currentStatus == GameStatus.play || currentStatus == GameStatus.pause){
            tileM.draw(g2);
            player.draw(g2);
        }
        if (currentStatus == GameStatus.pause){
            menuM.draw_pause_menu(g2);
        }

        g2.dispose();
    }
}
