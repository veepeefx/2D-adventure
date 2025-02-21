package Tiles;

import EntityObject.Object.SpruceTree;
import Main.GamePanel;
import Utilities.Tools;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class TileManagement {

    GamePanel gp;
    public Tile[] tileTypeArray;
    public int[][] mapInNums;

    String mapFilePath = "/Maps/forest_map";
    String tileDataFilePath = "/Maps/forest_map_data";

    ArrayList<String> fileNames = new ArrayList<>();
    ArrayList<String> collisionStatus = new ArrayList<>();

    public TileManagement(GamePanel gp) {

        this.gp = gp;
        // map size is given
        mapInNums = new int[gp.worldColumnLimit][gp.worldRowLimit];

        // loads all data about tiles
        load_tile_data(tileDataFilePath);
        // loads tiles and needed images also sets if tile is walkable or not
        load_tiles();
        // loads map to mapInNums
        load_map(mapFilePath);
    }

    // method reads tile data file (includes fileName and collisionStatus).
    // tile data is created at the same time as map and it includes all information about
    // tiles that is needed (filename and is tile walkable or not).
    private void load_tile_data(String filePath) {

        try(InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            String line;

            while((line = br.readLine()) != null){
                fileNames.add(line);
                collisionStatus.add(br.readLine());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // method makes new tile type to given index
    private void make_tile(int index, String imagePath, boolean collision) {

        Tools tool = new Tools();

        tileTypeArray[index] = new Tile();
        tileTypeArray[index].image = tool.load_image("/Tiles/" + imagePath);
        tileTypeArray[index].image = tool.scale_image(tileTypeArray[index].image,
                                                        gp.tileSize, gp.tileSize);
        tileTypeArray[index].collision = collision;
    }

    // method loads every type of tile to tileTypeArray
    private void load_tiles() {

        tileTypeArray = new Tile[fileNames.size()];

        for (int i = 0; i < fileNames.size(); i++){

            String fileName = fileNames.get(i);
            boolean collision = collisionStatus.get(i).equals("true");

            make_tile(i, fileName, collision);
        }
    }

    // method reads map (given as a text file) which contains numbers corresponding tiles
    // separated with spaces in between. map is saved into mapInNums.
    private void load_map(String filePath){

        try (InputStream is = getClass().getResourceAsStream(filePath);
             BufferedReader br = new BufferedReader(new InputStreamReader(is))){

            for (int i = 0; i < gp.worldRowLimit; i++) {

                String line = br.readLine();

                for (int j = 0; j < gp.worldColumnLimit; j++) {
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[j]);

                    if (num == 0 || num == 2){
                        num = randomization_handling(num, j, i);
                    }

                    mapInNums[j][i] = num;
                }
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int randomization_handling(int num, int col, int row) {

        Random random = new Random();
        int number = random.nextInt(0, 150);

        // num list
        // 0: grass (1 is randomization)
        // 2: water (3 is randomization)

        switch (num){
            case 0:
                if (number <= 10){
                    num = 1;
                } else if (number >= 145){
                    // spawns trees randomly
                    gp.objectsList.add(new SpruceTree(gp, col * gp.tileSize,
                                                            row * gp.tileSize));
                }
                break;
            case 2:
                if (number <= 5) {
                    num = 3;
                }
                break;
        }

        return num;
    }

    public void draw(Graphics2D g2){

        for (int i = 0; i < gp.worldRowLimit; i++) {
            for (int j = 0; j < gp.worldColumnLimit; j++) {

                int tileNum = mapInNums[j][i];

                // finding if that block is in screen and if it is its drawn to the
                // screen
                int worldX = j * gp.tileSize;
                int worldY = i * gp.tileSize;
                int screenX = worldX - (int) gp.player.worldMapX + gp.player.screenX;
                int screenY = worldY - (int) gp.player.worldMapY + gp.player.screenY;

                if (worldX + gp.tileSize > gp.player.worldMapX - gp.player.screenX &&
                        worldX - gp.tileSize < gp.player.worldMapX + gp.player.screenX &&
                        worldY + gp.tileSize > gp.player.worldMapY - gp.player.screenY &&
                        worldY - gp.tileSize < gp.player.worldMapY + gp.player.screenY) {

                    g2.drawImage(tileTypeArray[tileNum].image,screenX, screenY,
                            null);
                }
            }
        }
    }
}
