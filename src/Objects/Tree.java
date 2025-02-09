package Objects;

import Main.GamePanel;
import Tools.Tools;

import java.awt.*;

public class Tree extends Object{


    public Tree(GamePanel gp, int x, int y){
        super(gp);
        this.x = x;
        this.y = y;
        // tree is multi tile 1 tile wide and 2 tiles high
        this.tileAmountX = 1;
        this.tileAmountY = 2;

        Tools tool = new Tools();
        // load scaled image
        image = tool.load_image("/Objects/spruce.png", gp.tileSize,
                                    2 * gp.tileSize);
    }

    @Override
    public void draw(Graphics2D g2) {

        int screenX = x - (int) gp.player.worldMapX + gp.player.screenX;
        int screenY = y - (int) gp.player.worldMapY + gp.player.screenY;

        if (x + gp.tileSize > gp.player.worldMapX - gp.player.screenX &&
                x - gp.tileSize < gp.player.worldMapX + gp.player.screenX &&
                y + 2 * gp.tileSize > gp.player.worldMapY - gp.player.screenY &&
                y - gp.tileSize < gp.player.worldMapY + gp.player.screenY) {

            g2.drawImage(image, screenX, screenY, null);
        }
    }
}
