package UI;

import Main.GamePanel;

import java.awt.*;

public class GameUI {

    GamePanel gp;
    public int selectedInventorySlot = 0;
    int firstSlotX;
    int firstSlotY;

    public GameUI(GamePanel gp){
        this.gp = gp;

        this.firstSlotX = (gp.screenWidth / 2) - (4 * gp.tileSize);
        this.firstSlotY = gp.screenHeight - 2 * gp.tileSize;
    }

    // INVENTORY

    public void draw_inventory(Graphics2D g2){

        g2.setColor(Color.WHITE);

        for (int i = 0; i < 8; i++){
            g2.drawRect(firstSlotX + i * gp.tileSize, firstSlotY, gp.tileSize, gp.tileSize);
            if (i == selectedInventorySlot){
                g2.setColor(new Color(0, 0, 0, 100));
                g2.fillRect(firstSlotX + i * gp.tileSize, firstSlotY, gp.tileSize, gp.tileSize);
                g2.setColor(Color.WHITE);
            }
        }

    }
}
