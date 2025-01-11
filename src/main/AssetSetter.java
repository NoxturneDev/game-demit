package main;

import entities.*;
import objects.I_Bowl;

public class AssetSetter {
    GamePanel gp;

    public int totalMonsterMap7;

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
//        i++;

//        gp.monsters[mapNum][i] = new MON_tuyul(gp);
//        gp.monsters[mapNum][i].direction = "up";
//        gp.monsters[mapNum][i].worldX = gp.tileSize * 16;
//        gp.monsters[mapNum][i].worldY = gp.tileSize * 10;
//        i++;

//        gp.monsters[mapNum][i] = new MON_tuyul(gp);
//        gp.monsters[mapNum][i].direction = "left";
//        gp.monsters[mapNum][i].worldX = gp.tileSize * 16;
//        gp.monsters[mapNum][i].worldY = gp.tileSize * 10;
//        i++;

        mapNum = 3;
        i = 0;
        gp.monsters[mapNum][i] = new MON_Pocong(gp);
        gp.monsters[mapNum][i].direction = "up";
        gp.monsters[mapNum][i].worldX = gp.tileSize * 23;
        gp.monsters[mapNum][i].worldY = gp.tileSize * 21;
        i++;
        gp.monsters[mapNum][i] = new MON_Pocong(gp);
        gp.monsters[mapNum][i].direction = "up";
        gp.monsters[mapNum][i].worldX = gp.tileSize * 23;
        gp.monsters[mapNum][i].worldY = gp.tileSize * 20;

        mapNum = 7;
        i = 0;
        gp.monsters[mapNum][i] = new MON_tuyul(gp);
        gp.monsters[mapNum][i].direction = "up";
        gp.monsters[mapNum][i].worldX = gp.tileSize * 22;
        gp.monsters[mapNum][i].worldY = gp.tileSize * 39;
        i++;
        gp.monsters[mapNum][i] = new MON_tuyul(gp);
        gp.monsters[mapNum][i].direction = "up";
        gp.monsters[mapNum][i].worldX = gp.tileSize * 23;
        gp.monsters[mapNum][i].worldY = gp.tileSize * 39;
        i++;
        gp.monsters[mapNum][i] = new MON_tuyul(gp);
        gp.monsters[mapNum][i].direction = "down";
        gp.monsters[mapNum][i].worldX = gp.tileSize * 23;
        gp.monsters[mapNum][i].worldY = gp.tileSize * 39;
        totalMonsterMap7 = i;

//        DUNGEON LEVEL 1
        mapNum = 91;
        i = 0;
        gp.monsters[mapNum][i] = new MON_Kunti(gp);
        gp.monsters[mapNum][i].direction = "up";
        gp.monsters[mapNum][i].worldX = gp.tileSize * 29;
        gp.monsters[mapNum][i].worldY = gp.tileSize * 24;

//        DUNGEON LEVEL 2
        mapNum = 92;
        i = 0;
        gp.monsters[mapNum][i] = new MON_Pocong(gp);
        gp.monsters[mapNum][i].direction = "up";
        gp.monsters[mapNum][i].worldX = gp.tileSize * 29;
        gp.monsters[mapNum][i].worldY = gp.tileSize * 24;

//        DUNGEON LEVEL 3
        mapNum = 93;
        i = 0;
        gp.monsters[mapNum][i] = new MON_Butoijo(gp);
        gp.monsters[mapNum][i].direction = "up";
        gp.monsters[mapNum][i].worldX = gp.tileSize * 29;
        gp.monsters[mapNum][i].worldY = gp.tileSize * 24;

//        DUNGEON LEVEL 4
        mapNum = 94;
        i = 0;
        gp.monsters[mapNum][i] = new MON_kuyang(gp);
        gp.monsters[mapNum][i].direction = "up";
        gp.monsters[mapNum][i].worldX = gp.tileSize * 29;
        gp.monsters[mapNum][i].worldY = gp.tileSize * 24;

//        DUNGEON LEVEL 5
        mapNum = 95;
        i = 0;
        gp.monsters[mapNum][i] = new MON_Blorong(gp);
        gp.monsters[mapNum][i].direction = "up";
        gp.monsters[mapNum][i].worldX = gp.tileSize * 29;
        gp.monsters[mapNum][i].worldY = gp.tileSize * 24;
    }

}
