package UI;

import Main.GamePanel;
import java.awt.*;

import static Utilities.Constants.GameStatus.*;

public class MenuUI {

    GamePanel gp;
    public int menuCommand = 0;

    public MenuUI(GamePanel gp){
        this.gp = gp;
    }

    // calculates the full lenght of the string, finds the middle point and calculates
    // correct position for it to be in the center of the screen
    private int get_x_coord_for_centered_text(String text, Graphics2D g2){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth / 2 - length / 2;
    }

    // MAIN MENU

    public void draw_main_menu(Graphics2D g2) {

        g2.setColor(Color.BLACK);
        g2.fillRect(0,0, gp.screenWidth, gp.screenHeight);

        String text;
        int x, y;
        g2.setColor(Color.WHITE);

        // correct prints to make the main menu
        g2.setFont(new Font("OCR A Extended", Font.PLAIN, 100));

        text = "2D ADVENTURE";
        x = get_x_coord_for_centered_text(text, g2);
        y = gp.tileSize * 4;
        g2.drawString(text, x, y);

        g2.setFont(new Font("OCR A Extended", Font.PLAIN, 50));

        text = "New Game";
        x = get_x_coord_for_centered_text(text, g2);
        y = gp.tileSize * 5;
        g2.drawString(text, x, y);
        if (menuCommand == 0){
            g2.drawString(">", x - gp.tileSize, y);
        }

        text = "Load Game";
        x = get_x_coord_for_centered_text(text, g2);
        y = gp.tileSize * 6;
        g2.drawString(text, x, y);
        if (menuCommand == 1){
            g2.drawString(">", x - gp.tileSize, y);
        }

        text = "Options";
        x = get_x_coord_for_centered_text(text, g2);
        y = gp.tileSize * 7;
        g2.drawString(text, x, y);
        if (menuCommand == 2){
            g2.drawString(">", x - gp.tileSize, y);
        }

        text = "Quit";
        x = get_x_coord_for_centered_text(text, g2);
        y = gp.tileSize * 8;
        g2.drawString(text,x,y);
        if (menuCommand == 3){
            g2.drawString(">", x - gp.tileSize, y);
        }
    }

    // PAUSE MENU

    public void draw_pause_menu(Graphics2D g2) {

        // making half transparent background for the pause menu
        g2.setColor(new Color(0,0,0, 200));
        g2.fillRect(0,0, gp.screenWidth, gp.screenHeight);

        String text;
        int x, y;
        g2.setColor(Color.WHITE);

        // correct prints to make the pause screen
        g2.setFont(new Font("OCR A Extended", Font.PLAIN, 100));

        text = "PAUSE";
        x = get_x_coord_for_centered_text(text, g2);
        y = gp.tileSize * 4;
        g2.drawString(text, x, y);

        g2.setFont(new Font("OCR A Extended", Font.PLAIN, 50));

        text = "Continue";
        x = get_x_coord_for_centered_text(text, g2);
        y = gp.tileSize * 5;
        g2.drawString(text, x, y);
        if (menuCommand == 0){
            g2.drawString(">", x - gp.tileSize, y);
        }

        text = "Save Game";
        x = get_x_coord_for_centered_text(text, g2);
        y = gp.tileSize * 6;
        g2.drawString(text, x, y);
        if (menuCommand == 1){
            g2.drawString(">", x - gp.tileSize, y);
        }

        text = "Options";
        x = get_x_coord_for_centered_text(text, g2);
        y = gp.tileSize * 7;
        g2.drawString(text, x, y);
        if (menuCommand == 2){
            g2.drawString(">", x - gp.tileSize, y);
        }

        text = "Main Menu";
        x = get_x_coord_for_centered_text(text, g2);
        y = gp.tileSize * 8;
        g2.drawString(text,x,y);
        if (menuCommand == 3){
            g2.drawString(">", x - gp.tileSize, y);
        }
    }

    // COMMAND HANDLING

    // executes the command chosen by the player
    public void execute_command(){

        if (gp.gameStatus == MENU){
            switch (menuCommand){
                // new game
                case 0:
                    gp.gameStatus = PLAY;
                    break;
                // load game
                case 1:
                    // to be implemented
                    break;
                // options
                case 2:
                    // to be implemented
                    break;
                // quit
                case 3:
                    gp.gameRunning = false;
                    break;
            }
        } else if (gp.gameStatus == PAUSE){
            switch (menuCommand){
                // continue
                case 0:
                    gp.gameStatus = PLAY;
                    break;
                // save game
                case 1:
                    // to be implemented
                    break;
                // options
                case 2:
                    // to be implemented
                    break;
                // main menu
                case 3:
                    gp.gameStatus = MENU;
                    break;
            }
        }
        menuCommand = 0;
    }
}
