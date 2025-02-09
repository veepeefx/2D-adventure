package Tiles;

import Main.GamePanel;
import Tools.Tools;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TileManagement {

    GamePanel gp;
    Tile[] tileTypeArray;
    int[][] mapInNums;

    ArrayList<String> fileNames = new ArrayList<>();
    ArrayList<String> collisionStatus = new ArrayList<>();

    public TileManagement(GamePanel gp) {

        this.gp = gp;

        mapInNums = new int[gp.worldColumnLimit][gp.worldRowLimit];

        load_tile_data();
        load_tiles();
        load_map("/Maps/test");
    }

    private void make_tile(int index, String imagePath, boolean collision) {

        Tools tool = new Tools();

        try {
            tileTypeArray[index] = new Tile();
            tileTypeArray[index].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/" + imagePath));
            tileTypeArray[index].image = tool.scaleImage(tileTypeArray[index].image, gp.tileSize, gp.tileSize);
            tileTypeArray[index].collision = collision;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void load_tile_data() {

        InputStream is = getClass().getResourceAsStream("/Maps/test_data");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String line;

        try {
            while((line = br.readLine()) != null){
                fileNames.add(line);
                collisionStatus.add(br.readLine());
            }
            br.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void load_tiles() {

        tileTypeArray = new Tile[fileNames.size()];

        for (int i = 0; i < fileNames.size(); i++){
            String fileName;
            boolean collision;

            fileName = fileNames.get(i);
            if (collisionStatus.get(i).equals("true")){
                collision = true;
            } else {
                collision = false;
            }

            make_tile(i, fileName, collision);
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

                g2.drawImage(tileTypeArray[tileNum].image,screenX, screenY, null);
            }
            worldCol++;

            if (worldCol == gp.worldColumnLimit){
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
