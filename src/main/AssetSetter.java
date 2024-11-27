package main;

import entities.MON_tuyul;
import entities.NPC_Dukun;
import objects.I_Bowl;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setNPC() {
//       set each npc and store into gamepanels' npc[] array
        int mapNum = 0;
        int i = 0;
        gp.npc[i] = new NPC_Dukun(gp);
        gp.npc[i].worldX = gp.tileSize * 3;
        gp.npc[i].worldY = gp.tileSize * 3;
        i++;
    }

    public void setItem() {
        int mapNum = 0;
        int i = 0;
        gp.items[i] = new I_Bowl(gp);
        gp.items[i].worldX = gp.tileSize * 6;
        gp.items[i].worldY = gp.tileSize * 6;
        i++;
    }

    public void setMonsters() {
        int mapNum = 0;
        int i = 0;

        gp.monsters[i] = new MON_tuyul(gp);
        gp.monsters[i].worldX = gp.tileSize * 4;
        gp.monsters[i].worldY = gp.tileSize * 4;
        i++;

        gp.monsters[i] = new MON_tuyul(gp);
        gp.monsters[i].direction = "up";
        gp.monsters[i].worldX = gp.tileSize * 4;
        gp.monsters[i].worldY = gp.tileSize * 8;
        i++;

        gp.monsters[i] = new MON_tuyul(gp);
        gp.monsters[i].direction = "left";
        gp.monsters[i].worldX = gp.tileSize * 9;
        gp.monsters[i].worldY = gp.tileSize * 3;
        i++;
    }
}
