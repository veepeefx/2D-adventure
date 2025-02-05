package Main;

import javax.swing.*;

public class Main {

    public static void main (String[] args){

        JFrame gameWindow = new JFrame();
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setResizable(false);

        gameWindow.setTitle("2D Adventure");

        GamePanel game = new GamePanel();
        gameWindow.add(game);

        gameWindow.pack();
        gameWindow.setLocationRelativeTo(null); // sets window in the middle of the screen
        gameWindow.setVisible(true);

    }
}
