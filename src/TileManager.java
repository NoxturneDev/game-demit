import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {
    GamePanel gp;
    public Tile[] tiles;
    int mapTileNum[][];

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tiles = new Tile[10];
        mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];

        getTileImage();
        loadImage();
    }

    public void loadImage() {
        try {
            InputStream is = Objects.requireNonNull(getClass().getResourceAsStream("maps01.txt"));
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
                String line = br.readLine();

                while (col < gp.maxScreenCol) {
                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }

                if (col == gp.maxScreenCol) {
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
        try {
            tiles[0] = new Tile();
            tiles[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("grass.png")));

            tiles[1] = new Tile();
            tiles[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("stone.png")));

            tiles[2] = new Tile();
            tiles[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("water.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
            int tileNum = mapTileNum[col][row];

            g2.drawImage(tiles[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
            col++;
            x += gp.tileSize;

            if (col == gp.maxScreenCol) {
                col = 0;
                x = 0;
                row++;
                y += gp.tileSize;
            }
        }
    }
}
