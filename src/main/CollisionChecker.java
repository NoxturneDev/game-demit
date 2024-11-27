package main;

import entities.Entity;

public class CollisionChecker {
    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTileCollision(Entity entity) {

        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tm.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tm.mapTileNum[entityRightCol][entityTopRow];
                if (gp.tm.tiles[tileNum1].collision || gp.tm.tiles[tileNum2].collision) {
                    entity.collision = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tm.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tm.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tm.tiles[tileNum1].collision || gp.tm.tiles[tileNum2].collision) {
                    entity.collision = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tm.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tm.mapTileNum[entityLeftCol][entityTopRow];
                if (gp.tm.tiles[tileNum1].collision || gp.tm.tiles[tileNum2].collision) {
                    entity.collision = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tm.mapTileNum[entityRightCol][entityBottomRow];
                tileNum2 = gp.tm.mapTileNum[entityRightCol][entityTopRow];
                if (gp.tm.tiles[tileNum1].collision || gp.tm.tiles[tileNum2].collision) {
                    entity.collision = true;
                }
                break;
        }

    }

    public int checkEntity(Entity entity, Entity[] target)
    {
        int index = 999;   // no collision returns 999;
        //Use a temporal direction when it's being knockbacked
        String direction = entity.direction;
//        if(entity.knockBack == true)
//        {
//            direction = entity.knockBackDirection;
//        }

        for(int i = 0;i < target.length; i++)
        {
            if(target[i] != null)
            {
                // get entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                // get the object's solid area position
                target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;       //entity's solid area and obj's solid area is different.
                target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;

                switch (direction)
                {
                    case "up" :
                        entity.solidArea.y -= entity.speed;
                        break;
                    case "down" :
                        entity.solidArea.y += entity.speed;
                        break;
                    case "left" :
                        entity.solidArea.x -= entity.speed;
                        break;
                    case "right" :
                        entity.solidArea.x += entity.speed;
                        break;
                }

                if(entity.solidArea.intersects(target[i].solidArea))
                {
                    if(target[i] != entity) // avoid entity includes itself as a collision target
                    {
                        entity.collision = true;
                        index = i;   // Non-player characters cannot pickup objects.
                    }
                }
                entity.solidArea.x = entity.defaultSolidAreaX; //Reset
                entity.solidArea.y = entity.defaultSolidAreaY;

                target[i].solidArea.x = target[i].defaultSolidAreaX;     //Reset
                target[i].solidArea.y = target[i].defaultSolidAreaY;
            }
        }
        return index;
    }

    public void checkEntityToPlayerCollision(Entity entity) {
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;

        // get the object's solid area position
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;       //entity's solid area and obj's solid area is different.
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

        switch (entity.direction)
        {
            case "up" :
                entity.solidArea.y -= entity.speed;
                break;
            case "down" :
                entity.solidArea.y += entity.speed;
                break;
            case "left" :
                entity.solidArea.x -= entity.speed;
                break;
            case "right" :
                entity.solidArea.x += entity.speed;
                break;
        }
        if(entity.solidArea.intersects(gp.player.solidArea))
        {
            entity.collision = true;
        }
        entity.solidArea.x = entity.defaultSolidAreaX; ////Reset
        entity.solidArea.y = entity.defaultSolidAreaY;

        gp.player.solidArea.x = gp.player.defaultSolidAreaX;     ////Reset
        gp.player.solidArea.y = gp.player.defaultSolidAreaY;
    }
}
