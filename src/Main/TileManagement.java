package Main;

import Tiles.Tile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
public class TileManagement {

    GamePanel gp;

    Tile[] tileTypeArray;
    int[][] mapInNums;

    public TileManagement(GamePanel gp) {

        this.gp = gp;
        // how many different tile types can be
        tileTypeArray = new Tile[10];
        mapInNums = new int[gp.worldColumnLimit][gp.worldRowLimit];

        // loads all tile types to tileTypeArray
        load_tiles();
        load_map("/Maps/test_map_50_50_2");
    }

    private void load_tiles() {

        try {

            tileTypeArray[0] = new Tile();
            tileTypeArray[0].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/grass.png"));

            tileTypeArray[1] = new Tile();
            tileTypeArray[1].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/sand.png"));

            tileTypeArray[2] = new Tile();
            tileTypeArray[2].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/water.png"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void load_map(String mapFilePath){

        try {
            InputStream is = getClass().getResourceAsStream(mapFilePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0, row = 0;

            while (col < gp.worldColumnLimit && row < gp.worldRowLimit) {

                String line = br.readLine();
                while (col < gp.worldColumnLimit) {

                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);

                    mapInNums[col][row] = num;
                    col++;
                }
                if (col == gp.worldColumnLimit){
                    col = 0;
                    row++;
                }
            }
            br.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Graphics2D g2){

        int worldCol = 0, worldRow = 0;

        while (worldCol < gp.worldColumnLimit && worldRow < gp.worldRowLimit) {

            int tileNum = mapInNums[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - (int) gp.player.worldMapX + gp.player.screenX;
            int screenY = worldY - (int) gp.player.worldMapY + gp.player.screenY;

            if (worldX + gp.tileSize > gp.player.worldMapX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldMapX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldMapY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldMapY + gp.player.screenY) {

                g2.drawImage(tileTypeArray[tileNum].image,screenX, screenY, gp.tileSize, gp.tileSize, null);
            }
            worldCol++;

            if (worldCol == gp.worldColumnLimit){
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
