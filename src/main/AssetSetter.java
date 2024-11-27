package main;

import entities.MON_tuyul;
import entities.NPC_Dukun;
import objects.I_Bowl;

public class AssetSetter {
   GamePanel gp;

   public AssetSetter(GamePanel gp) {
       this.gp = gp;
   }

   public void setNPC () {
//       set each npc and store into gamepanels' npc[] array
       int mapNum = 0;
       int i = 0;
       gp.npc[i] = new NPC_Dukun(gp);
       gp.npc[i].worldX = gp.tileSize*3;
       gp.npc[i].worldY = gp.tileSize*3;
       i++;
   }

   public void setItem() {
        int mapNum = 0;
        int i = 0;
        gp.items[i] = new I_Bowl(gp);
        gp.items[i].worldX = gp.tileSize*6;
        gp.items[i].worldY = gp.tileSize*6;
        i++;
   }
}
