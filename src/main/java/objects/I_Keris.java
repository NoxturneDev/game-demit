package objects;

import main.GamePanel;

public class I_Keris extends Items {
    public I_Keris(GamePanel gp) {
        super(gp);
        itemName = "Keris";
        itemDescription = "Keris berasal dari Jawa Tengah. \n biasa digunakan dalam \n acara adat dan upacara \nadat. Keris dahulu kala \n digunakan sebagai ritual \n budaya";
        down1 = loadImage("/maps/keris.png", gp.tileSize, gp.tileSize);
        itemIcon = down1;
    }
}
