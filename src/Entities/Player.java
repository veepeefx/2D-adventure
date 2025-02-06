package Entities;

import Main.GamePanel;
import Main.Keylogger;

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

        screenX = gp.screenColumns * gp.tileSize / 2 - gp.tileSize / 2;
        screenY = gp.screenRows * gp.tileSize / 2 - gp.tileSize / 2;

        InputStream is = getClass().getResourceAsStream("/Entities/Player/player.png");

        try{
            image = ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void set_start_values(){
        this.speed = 4;
        this.worldMapX = gp.tileSize * 10;
        this.worldMapY = gp.tileSize * 10;
    }

    public void draw(Graphics2D g2){
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
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