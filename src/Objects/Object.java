package Objects;

import Main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Object {

    // coords (top left corner of the object)
    public int x, y;
    // saving size of the object for draw method. if size is not 1 x 1 tiles we need to
    // draw it differently
    int tileAmountX = 1, tileAmountY = 1;

    BufferedImage image;
    GamePanel gp;

    public Object(GamePanel gp){
        this.gp = gp;
    }

    public void draw(Graphics2D g2){

        int screenX = x - (int) gp.player.worldMapX + gp.player.screenX;
        int screenY = y - (int) gp.player.worldMapY + gp.player.screenY;

        // we need to take in count the tileAmount (only to positive direction since
        // coords are in the top left corner)
        if (x + tileAmountX * gp.tileSize > gp.player.worldMapX - gp.player.screenX &&
                x - gp.tileSize < gp.player.worldMapX + gp.player.screenX &&
                y + tileAmountY * gp.tileSize > gp.player.worldMapY - gp.player.screenY &&
                y - gp.tileSize < gp.player.worldMapY + gp.player.screenY) {

            g2.drawImage(image, screenX, screenY, null);
        }
    }
}
