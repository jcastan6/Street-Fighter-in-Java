package GameFiles;

import GameFiles.Characters.GameCharacter;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Camera {

    private int x;
    private int y;
    private int inity;
    private int width;
    private int height;
    private int centerx;
    private int centery;

    private Image healthLeft;
    private Image healthRight;
    private Image superRight;
    private Image superLeft;

    private Font superfont;

    private GameCharacter character;
    private GameCharacter character2;

    public Camera(GameCharacter character, GameCharacter character2, int width, int height){
        this.width = 900;
        this.height = height/2;
        //this.x = width/4;
        this.x = character2.getX()-100;
        this.inity = character.getY()-476;
        this.y = inity;
        centerx = x+width/2;
        centery = y+height/4;

        this.character = character;
        this.character2 = character2;

        healthLeft = new ImageIcon("FightingGame/Resources/Sprites/" + character.getName() +"/healthleft.png").getImage().getScaledInstance(425,70,Image.SCALE_SMOOTH);
        healthRight = new ImageIcon("FightingGame/Resources/Sprites/" + character2.getName() +"/healthright.png").getImage().getScaledInstance(425,70,Image.SCALE_SMOOTH);

        superRight = new ImageIcon("FightingGame/Resources/Sprites/superright.png").getImage().getScaledInstance(176*2,19*2,Image.SCALE_SMOOTH);
        superLeft = new ImageIcon("FightingGame/Resources/Sprites/superleft.png").getImage().getScaledInstance(176*2,19*2,Image.SCALE_SMOOTH);

        try {
            superfont = Font.createFont(Font.TRUETYPE_FONT, new File("FightingGame/Resources/7.ttf")).deriveFont(24f);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void update(GameCharacter character, GameCharacter character2){
        this.character = character;
        this.character2 = character2;

        if(character.getX() > character2.getX()){
            checkX(character,character2);
        }
        else{
            checkX(character2,character);
        }

        if(character.getY() < character2.getY()) {
            if (character.getY() - 375 < this.inity) {
                y = character.getY() - 375;
            }
        }
        else if(character.getY() > character2.getY()){
            if (character2.getY() - 375 < this.inity) {
                y = character2.getY() - 375;
            }
        }
        else {
            y = inity;
        }

        if(x >= 900){
            x = 900;
        }
        else if(x <= 65){
            x = 65;
        }

        centerx = x+width/2;
    }

    private void checkX(GameCharacter ch, GameCharacter ch2){
        ch.setFacingleft(true);
        ch2.setFacingleft(false);

        if (ch2.getX() < x) {
            x -= 3;
            ch2.setCanMove(2);
        } else {
            ch2.setCanMove(0);
        }

        if (ch.getX()+100 > x + width) {
            x += 3;
            ch.setCanMove(1);
        } else {
            ch.setCanMove(0);
        }
    }

    public int getY() {
        return centery+250+(y-inity);
    }

    public int getX() {
        return x;
    }

    public void drawCamera(Graphics g){

        //draw camera box
        if(GameWorld.debug) {
            g.setColor(new Color(20, 20, 22));
            g.drawRect(x, y, width, height);
            g.fillOval(centerx, centery, 20, 20);
        }

        //draw health bars for each character

        setColor(character, g);
        g.fillRect(x+17,y+5+(inity-y)/2,(int)(4*character.getHealth()),15);
        g.drawImage(healthLeft, x, y+(inity-y)/2, null);

        setColor(character2,g);
        g.fillRect(x+width - 15 -(int)(4*character2.getHealth()),y+5+(inity-y)/2,(int)(4*character2.getHealth()),15);
        g.drawImage(healthRight,x+width-425, y+(inity-y)/2, null);

        g.setColor(Color.BLUE);
        if(character.getSuperbar() == 300)
        {
            g.fillRect(x + 80, inity + 470, (int) (2.7 * 100), 13);
        }
        else {
            g.fillRect(x + 80, inity + 470, (int) (2.7 * (character.getSuperbar()%100)), 13);
        }

        if(character2.getSuperbar() == 300){
            g.fillRect(x+width-(int) (2.7*100)-80,inity+470,(int)(2.7*100),15);
        }
        else{
            g.fillRect(x+width-(int) (2.7*(character2.getSuperbar()%100))-80,inity+470,(int) (2.7*(character2.getSuperbar()%100)),15);
        }

        g.setFont(superfont);
        g.setColor(Color.gray);
        g.drawImage(superLeft, x, inity+450,null);
        g.drawString(Integer.toString(character.getSuperbar()/100), x+38,inity+479);
        g.drawImage(superRight, x+width-176*2, inity+450, null);
        g.drawString(Integer.toString(character2.getSuperbar()/100), x+width-57,inity+479);
    }

    private void setColor(GameCharacter character, Graphics g){
        if(character.getHealth() == 100) {
            g.setColor(Color.green);
        }
        else if(character.getHealth() <= 25){
            g.setColor(Color.red);
        }
        else g.setColor(Color.yellow);
    }

    public void drawGameOver(Graphics g){
        g.setColor(Color.red);
        g.setFont(superfont.deriveFont(100f));
        g.drawString("K.O",centerx-90, centery);
        g.setFont(superfont.deriveFont(20f));
    }

}
