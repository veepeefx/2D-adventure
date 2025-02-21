package EntityObject;

import Main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class EntityObject {

    GamePanel gp;
    public double worldMapX, worldMapY;
    public Rectangle hitBox;

    // animation sprites
    public BufferedImage[][] animations;
    public BufferedImage image;

    // if multi tile change these to the right dimensions
    public int widthInTiles = 1, heightInTiles = 1;

    // entity specific
    public int speed;
    public int direction;

    public EntityObject(GamePanel gp, int x, int y){
        this.gp = gp;
        this.worldMapX = x;
        this.worldMapY = y;

        // hitBox default is the size of the whole tile
        hitBox = new Rectangle(0, 0, gp.tileSize, gp.tileSize);
    }

    public void draw(Graphics2D g2){

        int screenX = (int) (worldMapX - gp.player.worldMapX + gp.player.screenX);
        int screenY = (int) (worldMapY - gp.player.worldMapY + gp.player.screenY);

        // for multi tiles we need to compensate the size
        screenX = screenX - (widthInTiles - 1) * gp.tileSize;
        screenY = screenY - (heightInTiles - 1) * gp.tileSize;

        // rendering all in sight as well as one tile to each direction as we can peek to
        // see non rendered tiles as well as we need to draw the multi tiles further
        // tiles even if lower tile isn't in the screen
        if (worldMapX + gp.tileSize > gp.player.worldMapX - gp.player.screenX &&
            worldMapX - widthInTiles * gp.tileSize < gp.player.worldMapX + gp.player.screenX &&
            worldMapY + gp.tileSize > gp.player.worldMapY - gp.player.screenY &&
            worldMapY - heightInTiles * gp.tileSize < gp.player.worldMapY + gp.player.screenY) {

            g2.drawImage(image, screenX, screenY, null);
        }
    }
}
