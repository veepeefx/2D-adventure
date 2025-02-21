package Utilities;

import EntityObject.EntityObject;
import Main.GamePanel;

import java.awt.*;

import static Utilities.Constants.Direction.*;
import static java.lang.Math.abs;

public class CollisionCheck {

    GamePanel gp;

    public CollisionCheck(GamePanel gp) {
        this.gp = gp;
    }

    // shows all in screen hit boxes
    public void draw_hit_box(Graphics2D g2){

        for (EntityObject object : gp.objectsList){
            g2.setColor(Color.RED);

            double worldMapX = object.worldMapX + object.hitBox.x, worldMapY = object.worldMapY + object.hitBox.y;

            int screenX = (int) (worldMapX - gp.player.worldMapX + gp.player.screenX);
            int screenY = (int) (worldMapY - gp.player.worldMapY + gp.player.screenY);

            // rendering all in sight as well as one tile to each direction as we can peek to
            // see non rendered tiles
            if (worldMapX + gp.tileSize > gp.player.worldMapX - gp.player.screenX &&
                    worldMapX - gp.tileSize < gp.player.worldMapX + gp.player.screenX &&
                    worldMapY + gp.tileSize > gp.player.worldMapY - gp.player.screenY &&
                    worldMapY - gp.tileSize < gp.player.worldMapY + gp.player.screenY) {

                g2.drawRect(screenX, screenY, object.hitBox.width, object.hitBox.height);
            }
        }
    }

    // checks if player is trying to walk into tiles that are not walkable. return true
    // if player is colliding with tile that cannot be walked on.
    public boolean tile_entity_collision(EntityObject entity){
        // entity can be between in 4 different tiles maximum we can save all columns and
        // rows of tiles which player is at the moment
        int leftCol = (int)(entity.worldMapX + entity.hitBox.x) / gp.tileSize;
        int rightCol = (int)(entity.worldMapX + entity.hitBox.x + entity.hitBox.width)
                                                                / gp.tileSize;
        int topRow = (int)(entity.worldMapY + entity.hitBox.y) / gp.tileSize;
        int bottomRow = (int)(entity.worldMapY + entity.hitBox.y + entity.hitBox.height)
                                                                / gp.tileSize;

        int tileNum1 = 0, tileNum2 = 0;

        // saving 2 of those tiles player is going to
        switch (entity.direction){
            case RIGHT:
                tileNum1 = gp.tileM.mapInNums[rightCol][topRow];
                tileNum2 = gp.tileM.mapInNums[rightCol][bottomRow];
                break;
            case LEFT:
                tileNum1 = gp.tileM.mapInNums[leftCol][topRow];
                tileNum2 = gp.tileM.mapInNums[leftCol][bottomRow];
                break;
            case DOWN:
                tileNum1 = gp.tileM.mapInNums[rightCol][bottomRow];
                tileNum2 = gp.tileM.mapInNums[leftCol][bottomRow];
                break;
            case UP:
                tileNum1 = gp.tileM.mapInNums[rightCol][topRow];
                tileNum2 = gp.tileM.mapInNums[leftCol][topRow];
                break;
        }

        // returning whether collision happens or doesn't
        return gp.tileM.tileTypeArray[tileNum1].collision ||
                gp.tileM.tileTypeArray[tileNum2].collision;
    }

    // checks if entity hits another entity or object. returns true if so.
    public boolean entity_object_collision(EntityObject entity){

        // making new rectangle to correct position in world map (entity hitBox coords
        // are relative to entity coords)
        Rectangle eRectangle = new Rectangle((int)entity.worldMapX + entity.hitBox.x,
                (int)entity.worldMapY + entity.hitBox.y,
                entity.hitBox.width, entity.hitBox.height);

        // going trough all objects and entities
        for (EntityObject object : gp.objectsList){

            // if object is further away we don't need to check them
            if (abs(object.worldMapX - entity.worldMapX) > gp.tileSize ||
                    abs(object.worldMapY - entity.worldMapY) > gp.tileSize){
                continue;
            }

            // entity belongs to objectsList so we must skip it
            if (entity == object){
                continue;
            }

            // making rectangle to the object as well
            Rectangle oRectangle = new Rectangle((int)object.worldMapX + object.hitBox.x,
                    (int)object.worldMapY + object.hitBox.y,
                    object.hitBox.width, object.hitBox.height);

            // if rectangles are colliding can we return true
            if (eRectangle.intersects(oRectangle)){
                return true;
            }
        }
        // if none of the objects/entities are colliding we return false
        return false;
    }
}
