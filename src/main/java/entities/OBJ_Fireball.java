package entities;

import main.GamePanel;

public class OBJ_Fireball extends Projectile {
    GamePanel gp;

    public OBJ_Fireball(GamePanel gp) {
        super(gp);
        this.gp = gp;
        this.attack = 2;
        this.maxLife = 100;
        this.speed = 10;
        this.alive = false;
        getImage();
    }

    public void getImage() {
        up1 = loadImage("/projectiles/fire_right_1.png", gp.tileSize, gp.tileSize);
        up2 = loadImage("/projectiles/fire_right_1.png", gp.tileSize, gp.tileSize);
        left1 = loadImage("/projectiles/fire_right_1.png", gp.tileSize, gp.tileSize);
        left2 = loadImage("/projectiles/fire_right_1.png", gp.tileSize, gp.tileSize);
        right1 = loadImage("/projectiles/fire_right_1.png", gp.tileSize, gp.tileSize);
        right2 = loadImage("/projectiles/fire_right_1.png", gp.tileSize, gp.tileSize);
        down1 = loadImage("/projectiles/fire_right_1.png", gp.tileSize, gp.tileSize);
        down2 = loadImage("/projectiles/fire_right_1.png", gp.tileSize, gp.tileSize);
    }
}
