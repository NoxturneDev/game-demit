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
        gp.npc[mapNum][i] = new NPC_Dukun(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 24;
        gp.npc[mapNum][i].worldY = gp.tileSize * 29;
        i++;
    }

    public void setItem() {
        int mapNum = 0;
        int i = 0;
//        gp.items[mapNum][i] = new I_Bowl(gp);
//        gp.items[mapNum][i].worldX = gp.tileSize * 23;
//        gp.items[mapNum][i].worldY = gp.tileSize * 4;
        i++;
    }

    public void setMonsters() {
        int mapNum = 0;
        int i = 0;

//        gp.monsters[mapNum][i] = new MON_tuyul(gp);
//        gp.monsters[mapNum][i].worldX = gp.tileSize * 16;
//        gp.monsters[mapNum][i].worldY = gp.tileSize * 10;
        i++;

//        gp.monsters[mapNum][i] = new MON_tuyul(gp);
//        gp.monsters[mapNum][i].direction = "up";
//        gp.monsters[mapNum][i].worldX = gp.tileSize * 16;
//        gp.monsters[mapNum][i].worldY = gp.tileSize * 10;
        i++;

//        gp.monsters[mapNum][i] = new MON_tuyul(gp);
//        gp.monsters[mapNum][i].direction = "left";
//        gp.monsters[mapNum][i].worldX = gp.tileSize * 16;
//        gp.monsters[mapNum][i].worldY = gp.tileSize * 10;
        i++;
    }
}
