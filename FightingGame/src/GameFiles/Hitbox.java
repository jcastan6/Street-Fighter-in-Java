package GameFiles;

import java.awt.*;

public class Hitbox {
    private int x;
    private int y;
    private int width;
    private int height;

    public Hitbox(int x,int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void updateHitBox(int x,int y){
        this.x = x;
        this.y = y;
    }

    public void drawBox(Graphics g){
        g.setColor(new Color(20, 20, 22));
        g.drawRect(x,y,width,height);
    }

    public Rectangle getBox(){
        return new Rectangle(x,y,width,height);
    }
}
