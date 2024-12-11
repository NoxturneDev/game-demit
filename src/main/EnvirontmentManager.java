package main;

import java.awt.*;

public class EnvirontmentManager {
    GamePanel gp;
    public Lighting lighting;

    public EnvirontmentManager(GamePanel gp) {
        this.gp = gp;
    }

    public void setup() {
        lighting = new Lighting(gp);
    }

    public void draw(Graphics2D g2) {
        lighting.draw(g2);
    }
}
