package objects;

import main.GamePanel;

public class I_Bowl extends Items {
    public I_Bowl(GamePanel gp) {
        super(gp);
        itemName = "Mangkok";
        itemDescription = "Mangkok biasa dengan motif bunga yang indah \n dan warna cerah. Mangkok ini terbuat \n dari keramik yang kuat dan tahan lama.";
        down1 = loadImage("/player/mc_walk_down_1.png", gp.tileSize, gp.tileSize);
        itemIcon = down1;
    }
}
