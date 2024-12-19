package main;

import java.awt.*;

public class EventHandler {
    GamePanel gp;

    EventRect eventRect[][][];

    int eventRectDefaultX, eventRectDefaultY;
    public int MAP_FOREST_1 = 1;
    public int MAP_HOUSE_1 = 2;
    public int MAP_FOREST_2 = 3;
    public int MAP_FOREST_3 = 4;
    public int MAP_FOREST_4 = 5;
    public int MAP_FOREST_5 = 6;
    public int MAP_HOUSE_2 = 7;

    public EventHandler(GamePanel gp) {
        this.gp = gp;

        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        int map = 0;
        int col = 0;
        int row = 0;

        while (map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow) {
            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 20;
            eventRect[map][col][row].y = 20;
            eventRect[map][col][row].width = 8;
            eventRect[map][col][row].height = 8;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;

            col++;

            if (col == gp.maxWorldCol) {
                col = 0;
                row++;

                if (row == gp.maxWorldRow) {
                    row = 0;
                    map++;
                }
            }
        }
    }

    public void checkEvent() {
//        if (hit(0, 2, 4, "left") == true) {
//            System.out.println("Hit some event");
//        }

//        forest 1
        if (hit(0, 41, 30, "any") == true) {
            teleport(1, 21, 9, 3);
        }
//      house 1
        if (hit(1, 21, 11, "any") == true) {
            teleport(2, 40, 30, -1);
        }
//        forest 2
        if ((hit(2, 34, 30, "any") == true) || (hit(2, 34, 31, "any") == true)) {
            teleport(3, 23, 20, 4);
        }
//        forest 3
        if ((hit(3, 39, 10, "any") == true) || (hit(2, 39, 31, "any") == true)) {
            teleport(4, 23, 20, -1);
        }

//        forest 4
        if ((hit(4, 23, 28, "any") == true) || (hit(4, 24, 28, "any") == true)) {
            gp.sceneManager.playScene(6);
        }
        if ((hit(4, 23, 28, "any") == true) || (hit(4, 24, 28, "any") == true)) {
            teleport(5, 23, 16, -1);
        }

//        forest 5
        if ((hit(5, 41, 30, "any") == true) || (hit(5, 41, 31, "any") == true)) {
            teleport(6, 24, 38, -1);
        }

//        house 2
        if ((hit(5, 34, 30, "any") == true)) {
            teleport(6, 24, 38, -1);
        }

        if (hit(6, 20, 43, "any") == true) {
           gp.sceneManager.playScene(7);
        }

//        house 3
        if (hit(7, 24, 38, "any") == true) {
            gp.sceneManager.playScene(11);
        }
    }

    public boolean hit(int map, int col, int row, String reqDirection) {
        boolean hit = false;

        if (map == gp.currentMap) {
            gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
            gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

            eventRect[map][col][row].x = col * gp.tileSize + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row * gp.tileSize + eventRect[map][col][row].y;

            if (gp.player.solidArea.intersects(eventRect[map][col][row])) {
                if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                    hit = true;
                }
            }

            gp.player.solidArea.x = gp.player.defaultSolidAreaX;
            gp.player.solidArea.y = gp.player.defaultSolidAreaY;
            eventRect[map][col][row].x = eventRectDefaultX;
            eventRect[map][col][row].y = eventRectDefaultY;
        }

        return hit;
    }

    public void teleport(int map, int worldX, int worldY, int sceneIndex) {
        gp.currentMap = map;
        gp.player.worldX = worldX * gp.tileSize;
        gp.player.worldY = worldY * gp.tileSize;
//        gp.gameState = gp.QUIZ;
//        gp.ui.currentQuiz = "Pertanyaanya adalah";
//        gp.ui.currentQuizCorrectAnswer = 1;

//        invoke cutscene number 1
        if (sceneIndex != -1) {
            gp.sceneManager.playScene(sceneIndex);
        }
    }
}
