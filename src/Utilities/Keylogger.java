package Utilities;

import Main.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import static Utilities.Constants.GameStatus.*;


public class Keylogger implements KeyListener {

    public Boolean upPressed = false, downPressed = false,
                      leftPressed = false, rightPressed = false;

    GamePanel gp;

    public Keylogger(GamePanel gp){
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        if (gp.gameStatus == PLAY) {
            game_keypress_commands(code);
        } else if (gp.gameStatus == PAUSE ||
                    gp.gameStatus == MENU) {
            menu_keypress_commands(code);
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if (gp.gameStatus == PLAY){
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
                upPressed = false;
            }
            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
                downPressed = false;
            }
            if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT){
                leftPressed = false;
            }
            if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT){
                rightPressed = false;
            }
        }
    }

    private void game_keypress_commands(int code) {

        // player movement
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        }

        // opening pause menu
        if (code == KeyEvent.VK_ESCAPE){
            gp.gameStatus = PAUSE;
        }

        // accessing inventory
        if (code == KeyEvent.VK_1){
            gp.gameUI.selectedInventorySlot = 0;
        }
        if (code == KeyEvent.VK_2){
            gp.gameUI.selectedInventorySlot = 1;
        }
        if (code == KeyEvent.VK_3){
            gp.gameUI.selectedInventorySlot = 2;
        }
        if (code == KeyEvent.VK_4){
            gp.gameUI.selectedInventorySlot = 3;
        }
        if (code == KeyEvent.VK_5){
            gp.gameUI.selectedInventorySlot = 4;
        }
        if (code == KeyEvent.VK_6){
            gp.gameUI.selectedInventorySlot = 5;
        }
        if (code == KeyEvent.VK_7){
            gp.gameUI.selectedInventorySlot = 6;
        }
        if (code == KeyEvent.VK_8){
            gp.gameUI.selectedInventorySlot = 7;
        }

    }

    private void menu_keypress_commands(int code) {

        if (code == KeyEvent.VK_ENTER){
            gp.menuUI.execute_command();
        }

        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            gp.menuUI.menuCommand--;
            if (gp.menuUI.menuCommand < 0){
                gp.menuUI.menuCommand = 3;
            }
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            gp.menuUI.menuCommand++;
            if (gp.menuUI.menuCommand > 3){
                gp.menuUI.menuCommand = 0;
            }
        }
    }
}
