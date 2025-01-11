package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

public class TileManager {
    GamePanel gp;
    public Tile[] tiles;
    public int mapTileNum[][][];
    ArrayList<String> fileNames = new ArrayList<>();
    ArrayList<String> collisionStatus = new ArrayList<>();

    public TileManager(GamePanel gp) {
        this.gp = gp;

        //READ TILE DATA FILE
        InputStream is = getClass().getResourceAsStream("/maps/tileData.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        //GETTING TILE NAMES AND COLLISION INFO FROM THE FILE
        String line;
        try {
            while ((line = br.readLine()) != null) {
                fileNames.add(line);
                collisionStatus.add(br.readLine()); //read next line
            }
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //INITIALIZE THE TILE ARRAY BASED ON THE fileNames size
        tiles = new Tile[fileNames.size()]; // grass, wall, water00, water01...
        getTileImage();

        //GET THE maxWorldCol & Row
        is = getClass().getResourceAsStream("/maps/forest_1.txt");
        br = new BufferedReader(new InputStreamReader(is));

        try {
            String line2 = br.readLine();
            String maxTile[] = line2.split(" ");

            gp.maxWorldCol = maxTile.length;
            gp.maxWorldRow = maxTile.length;

            mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

            br.close();
        } catch (IOException e) {
            System.out.println("Exception!");
        }

        loadMap("/maps/forest_1.txt", 0);
        loadMap("/maps/house_1.txt", 1);
        loadMap("/maps/forest_2.txt", 2);
        loadMap("/maps/forest_3.txt", 3);
        loadMap("/maps/forest_4.txt", 4);
        loadMap("/maps/forest_5.txt", 5);
        loadMap("/maps/house_2.txt", 6);
        loadMap("/maps/house_3.txt", 7);
        loadMap("/maps/dungeon_0.txt", 91);
        loadMap("/maps/dungeon_0.txt", 92);
        loadMap("/maps/dungeon_0.txt", 93);
        loadMap("/maps/dungeon_0.txt", 94);
        loadMap("/maps/dungeon_0.txt", 95);
//        loadMap("/maps/world_map.txt", 1);
    }

    public void loadMap(String filePath, int map) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is)); // to read from txt

            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();

                while (col < gp.maxWorldCol) {
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt (numbers[col]);

                    mapTileNum[map][col][row] = num;
                    col++;
                }
                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void getTileImage() {
//        try {
//            tiles[0] = new Tile();
//            tiles[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/maps/grass.png")));
//
//            tiles[1] = new Tile();
//            tiles[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/maps/stone.png")));
//            tiles[1].collision = true;
//
//            tiles[2] = new Tile();
//            tiles[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/maps/water.png")));
//            tiles[2].collision = true;
//
//            tiles[3] = new Tile();
//            tiles[3].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/maps/tree.png")));
//            tiles[3].collision = true;
//
//            tiles[4] = new Tile();
//            tiles[4].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/maps/dirt.png")));
            for(int i = 0; i < fileNames.size(); i++)
            {
                String fileName;
                boolean collision;

                //Get a file name
                fileName = fileNames.get(i);

                //Get a collision status
                if(collisionStatus.get(i).equals("true"))
                {
                    collision = true;
                }
                else
                {
                    collision = false;
                }

                setup(i, fileName, collision);

            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public void setup(int index, String imageName, boolean collision)
    {                                                                       // IMPROVING RENDERING // Scaling with uTool
        UtilityTool uTool = new UtilityTool();                              // With uTool I'm not using anymore like: g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize,null);
        try                                                                 // I use g2.drawImage(tile[tileNum].image, screenX, screenY,null);
        {
            tiles[index] = new Tile();
            tiles[index].image = ImageIO.read(getClass().getResourceAsStream("/maps/"+ imageName));
            tiles[index].image = uTool.scaleImage(tiles[index].image, gp.tileSize, gp.tileSize);
            tiles[index].collision = collision;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow]; //drawing current map

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                g2.drawImage(tiles[tileNum].image, screenX, screenY, null);
            }

            worldCol++;

            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }

        }
    }
}
