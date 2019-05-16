package GameFiles;

import java.awt.*;

public class Hurtbox {
    private int x;
    private int y;
    private int width;
    private int height;

    private int canHit;
    private int hit;
    private int damage;

    public Hurtbox(int x,int y, int width, int height, int damage, int canHit){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        hit = 0;
        this.damage = damage;
    }

    public void updateHurtbox(int x,int y){
        this.x = x;
        this.y = y;
    }

    public void drawBox(Graphics g){
        g.setColor(new Color(210, 20, 22));
        g.drawRect(x,y,width,height);
    }

    public Rectangle getBox(){
        return new Rectangle(x,y,width,height);
    }

    public boolean canHit(){
        if(hit <= canHit){
            return true;
        }
        else{
            return false;
        }
    }

    public void hasHit(){
        hit+=1;
    }

    public int getDamage(){return damage;}

    public void reset(){hit = 0;}
}
