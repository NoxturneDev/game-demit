package objects;

import entities.Entity;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

// super class for all in game items
public class Items extends Entity {
    public String itemName = "Item";
    public String itemDescription = "This is an item";
    public boolean pickupAble = false;

    public Items(GamePanel gp) {
        super(gp);
    }

    public void draw(Graphics2D g2) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
                && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
                && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
                && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY
        ) {
           g2.drawImage(down1, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }
}
