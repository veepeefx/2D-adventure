package Entities;

import Main.GamePanel;
import Tools.Keylogger;
import Tools.Tools;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class Player extends Entity {

    GamePanel gp;
    Keylogger kl;

    public final int screenX, screenY;

    public Player(GamePanel gp, Keylogger kl){

        this.gp = gp;
        this.kl = kl;
        set_start_values();

        // sets player in the middle of the screen
        screenX = gp.screenWidth / 2 - gp.tileSize / 2;
        screenY = gp.screenHeight / 2 - gp.tileSize / 2;

        // load player image
        InputStream is = getClass().getResourceAsStream("/Entities/Player/player.png");
        try{
            image = ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // scaling image
        Tools tool = new Tools();
        image = tool.scaleImage(image, gp.tileSize, gp.tileSize);
    }

    // sets values for player speed and coords which player spawns
    private void set_start_values(){
        this.speed = 4;
        this.worldMapX = gp.tileSize * 10;
        this.worldMapY = gp.tileSize * 10;
    }

    public void draw(Graphics2D g2){
        g2.drawImage(image, screenX, screenY, null);
    }

    public void movement(){

        double dx = 0, dy = 0;
        // origo (0,0) is in the top left corner
        if (kl.upPressed){
            dy -= 1;
        }
        if (kl.downPressed){
            dy += 1;
        }
        if (kl.leftPressed){
            dx -= 1;
        }
        if (kl.rightPressed){
            dx += 1;
        }
        // if diagonal movement we need to normalize the lenght
        if (dx != 0 && dy != 0){
            dx = dx / Math.sqrt(2);
            dy = dy / Math.sqrt(2);
        }

        worldMapX += dx * speed;
        worldMapY += dy * speed;
    }
}