package objects;

import main.GamePanel;

public class I_Bowl extends Items {
    public I_Bowl(GamePanel gp) {
        super(gp);
        itemName = "Blankon";
        itemDescription = "Blankon berasal dari Jawa Tengah. \n biasa digunakan dalam acara adat \n dan upacara adat.";
        down1 = loadImage("/maps/blankon.png", gp.tileSize, gp.tileSize);
        itemIcon = down1;
    }
}
