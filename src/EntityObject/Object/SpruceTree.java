package EntityObject.Object;

import EntityObject.EntityObject;
import Main.GamePanel;
import Utilities.Tools;

import java.awt.*;
import static Utilities.Constants.GameConstants.*;

public class SpruceTree extends EntityObject {

    public SpruceTree(GamePanel gp, int x, int y){
        super(gp, x, y);

        // tree is multi tile 1 tile wide and 2 tiles high
        this.widthInTiles = 1;
        this.heightInTiles = 2;
        this.hitBox = new Rectangle(SCALE, 10 * SCALE, 14 * SCALE, 2 * SCALE);

        Tools tool = new Tools();
        // load scaled image
        image = tool.load_image("/Objects/spruce.png");
        image = tool.scale_image(image, gp.tileSize, 2* gp.tileSize);
    }
}
