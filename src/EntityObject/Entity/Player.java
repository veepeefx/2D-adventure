package EntityObject.Entity;

import EntityObject.EntityObject;
import Main.GamePanel;
import Tools.Keylogger;
import Tools.Tools;

import java.awt.*;

public class Player extends EntityObject {

    Keylogger kl;

    public final int screenX, screenY;

    public Player(GamePanel gp, Keylogger kl){

        super(gp, gp.tileSize * 2, (gp.worldRowLimit / 2) * gp.tileSize - gp.tileSize / 2);
        this.speed = 4;
        this.kl = kl;

        // sets player in the middle of the screen
        screenX = gp.screenWidth / 2 - gp.tileSize / 2;
        screenY = gp.screenHeight / 2 - gp.tileSize / 2;

        Tools tool = new Tools();
        // load scaled image
        image = tool.load_image("/Entities/Player/player.png", gp.tileSize, gp.tileSize);
    }

    @Override
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