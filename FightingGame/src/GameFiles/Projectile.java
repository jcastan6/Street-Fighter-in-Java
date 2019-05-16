package GameFiles;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Projectile {
    private int x;
    private int y;
    private int vx;
    private int vy;
    private Hurtbox hurtbox;
    private Image img;
    private boolean facingLeft;

    public Projectile(int x, int y, int vx, int vy, boolean facingLeft) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        hurtbox = new Hurtbox(x,y,75,75,30,1);
        this.img = new ImageIcon("FightingGame/Resources/Sprites/Ken/Projectile/hadoken.png").getImage();
        this.facingLeft = facingLeft;
    }

    public void moveForwards() {
        x += vx;
        y += vy;
    }

    public void update(){
        moveForwards();
        hurtbox.updateHurtbox(x,y);
    }

    public void drawImage(Graphics g) {
       if(facingLeft){
           g.drawImage(img, x+img.getWidth(null), y, -img.getWidth(null), img.getHeight(null), null );
       }
       else {
           g.drawImage(img, x, y, null);
       }

       if(GameWorld.debug) {
           hurtbox.drawBox(g);
       }
    }

    public Hurtbox getHurtbox(){return hurtbox;}
}
