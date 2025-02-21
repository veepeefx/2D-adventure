package EntityObject.Entity;

import EntityObject.EntityObject;
import Main.GamePanel;
import Utilities.Keylogger;
import Utilities.Tools;

import java.awt.*;
import java.awt.image.BufferedImage;

import static Utilities.Constants.ActionTypes.*;
import static Utilities.Constants.Direction.*;
import static Utilities.Constants.GameConstants.*;

public class Player extends EntityObject {

    Keylogger kl;
    GamePanel gp;
    public final int screenX, screenY;

    private int playerAction = IDLE;
    private int aniSpeed = 12;
    private int aniTick = 0;
    private int aniIndex = 0;

    public Player(GamePanel gp, Keylogger kl){

        super(gp, gp.tileSize * 2, (gp.worldRowLimit / 2) * gp.tileSize - gp.tileSize / 2);
        this.gp = gp;
        this.speed = 4;
        this.kl = kl;

        this.hitBox = new Rectangle(5 * SCALE, 10 * SCALE, 5 * SCALE, 4 * SCALE);
        this.direction = RIGHT;

        // sets player in the middle of the screen
        this.screenX = gp.screenWidth / 2 - gp.tileSize / 2;
        this.screenY = gp.screenHeight / 2 - gp.tileSize / 2;

        Tools tool = new Tools();
        // load scaled image
        BufferedImage rawImage = tool.load_image("/Entities/player.png");
        load_animations(rawImage, tool, gp);
    }

    private void load_animations(BufferedImage image, Tools tool, GamePanel gp) {

        // row indexes 0: idle right, 1: idle left, 2: idle down, 3: idle up
        //             4: walk right, 5: walk left, 6: walk down, 7: walk up

        animations = new BufferedImage[8][8];
        int row = 0, col;

        for (int i = 0; i < animations.length; i++) {
            col = 0;

            for (int j = 0; j < animations[i].length; j++) {

                // make another copy of right row but flipped (left row)
                if (i == 1 || i == 5){
                    animations[i][j] = tool.mirror_image(animations[i - 1][j]);

                } else {

                    int imageSize = 16;
                    int emptySpace = 32;

                    int x = (1 + (2 * col)) * emptySpace + col * imageSize;
                    int y = (1 + (2 * row)) * emptySpace + row * imageSize;

                    animations[i][j] = image.getSubimage(x, y, imageSize, imageSize);
                    animations[i][j] = tool.scale_image(animations[i][j], gp.tileSize, gp.tileSize);
                }
                col++;
            }

            // only increase row when used actual picture not when only mirrored
            if (i != 1 && i != 5){
                row++;
            }
        }
    }

    @Override
    public void draw(Graphics2D g2){

        aniTick++;
        if (aniTick >= aniSpeed){
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= get_player_sprite_amount(playerAction)){
                aniIndex = 0;
            }
        }

        // every movement has 4 directions (idle indexed 0-3, walking 4-7 etc..)
        int index = playerAction * 4 - (4 - direction);

        g2.drawImage(animations[index][aniIndex], screenX, screenY, null);
    }

    public void movement(){

        double dx = 0, dy = 0;
        // origo (0,0) is in the top left corner
        if (kl.upPressed){
            direction = UP;
            dy -= 1;
        }
        if (kl.downPressed){
            direction = DOWN;
            dy += 1;
        }
        if (kl.leftPressed){
            direction = LEFT;
            dx -= 1;
        }
        if (kl.rightPressed){
            direction = RIGHT;
            dx += 1;
        }

        // changing between different player animations and animation speeds
        if (!kl.downPressed && !kl.upPressed && !kl.leftPressed && !kl.rightPressed){
            playerAction = IDLE;
            aniSpeed = 12;
        } else {
            playerAction = WALK;
            aniSpeed = 5;
        }

        // if diagonal movement we need to normalize the lenght
        if (dx != 0 && dy != 0){
            dx = dx / Math.sqrt(2);
            dy = dy / Math.sqrt(2);
        }

        // saving previous position to be used if player collides to something
        double tempX = worldMapX;
        double tempY = worldMapY;

        // updating players position
        worldMapX += dx * speed;
        worldMapY += dy * speed;

        // checking collision between player and tiles. checks if collision happens with
        // two tiles in front of player.
        if (gp.collisionC.tile_entity_collision(this)){
            worldMapX = tempX;
            worldMapY = tempY;

        // checking collision between player and close by entities/objects. if collision
        // happens then we need to revert the changes and go back to previous position
        // where player didn't hit anything. 2 more if statements to handle if player is
        // dragging along walls
        } else if (gp.collisionC.entity_object_collision(this)){
            worldMapX = tempX;
            if (gp.collisionC.entity_object_collision(this)){
                worldMapX += dx * speed;
                worldMapY = tempY;
                if (gp.collisionC.entity_object_collision(this)){
                    worldMapX = tempX;
                }
            }
        }
    }
}