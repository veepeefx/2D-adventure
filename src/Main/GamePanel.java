package Main;

import EntityObject.Entity.Player;
import EntityObject.EntityObject;
import UI.GameUI;
import UI.MenuUI;
import Tiles.TileManagement;
import Utilities.Keylogger;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

import static Utilities.Constants.GameStatus.*;
import static Utilities.Constants.GameConstants.*;

public class GamePanel extends JPanel implements Runnable {

    // screen settings
    public final int tileSize = TILE_PX_SIZE * SCALE;
    public final int screenWidth = tileSize * SCREEN_COLUMNS;
    public final int screenHeight = tileSize * SCREEN_ROWS;

    // map size
    public int worldColumnLimit = 50;
    public int worldRowLimit = 50;

    // target FPS
    final int FPS = 60;
    // game is started to be in main menu
    public int gameStatus = MENU;

    // true until game has ended (player has pushed quit button)
    public boolean gameRunning = true;

    public Thread gameThread;
    public Keylogger keylogger = new Keylogger(this);

    // entities and objects
    public ArrayList<EntityObject> objectsList = new ArrayList<>();
    public Player player = new Player(this, keylogger);

    // tiles
    TileManagement tileM = new TileManagement(this);

    // UI
    public MenuUI menuUI = new MenuUI(this);
    public GameUI gameUI = new GameUI(this);

    GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(new Color(48, 202, 255));
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
        objectsList.add(player);

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

        // objectsList move only if currentStatus is play
        if (gameStatus == PLAY) {
            player.movement();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        if (gameStatus == MENU) {
            menuUI.draw_main_menu(g2);
        }

        // game shows behind the pause screen so we need to update it even thought game
        // is paused
        if (gameStatus == PLAY || gameStatus == PAUSE) {

            tileM.draw(g2);

            // sorts all objects from back to front
            sort_objects();
            for (EntityObject object : objectsList){
                object.draw(g2);
            }

            gameUI.draw_inventory(g2);

            if (gameStatus == PAUSE) {
                menuUI.draw_pause_menu(g2);
            }
        }
        g2.dispose();
    }

    private void sort_objects() {

        objectsList.sort(new Comparator<EntityObject>() {
            @Override
            public int compare(EntityObject o1, EntityObject o2) {
                return Double.compare(o1.worldMapY, o2.worldMapY);
            }
        });
    }
}
