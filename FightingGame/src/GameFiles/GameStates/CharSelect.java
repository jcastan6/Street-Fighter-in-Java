package GameFiles.GameStates;


import GameFiles.CharacterStates.Animations.Animation;
import GameFiles.CharacterStates.Animations.StandbyAnimation;
import GameFiles.GameWorld;
import GameFiles.Sound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CharSelect implements KeyListener {
    private ArrayList<Animation> animations = new ArrayList<>();
    private static ArrayList<String> characters = new ArrayList<>();
    private ArrayList<ImageIcon> portraits = new ArrayList<>();
    private long currTime = 0;
    private Font font;

    private int player1 = 0;
    private int player2 = 0;

    private final int p1;
    private final int p2;
    private final int start;
    private final int exit;
    private boolean done = false;

    static {
        characters.add("Ken");
        characters.add("Dudley");
    }

    public CharSelect(){
        for (String character: characters) {
            animations.add(new StandbyAnimation(character, "Standing", 0,0,50));
            portraits.add(new ImageIcon("FightingGame/Resources/Sprites/"+character+"/"+character+".png"));
        }

        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("FightingGame/Resources/7.ttf")).deriveFont(72f);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.exit = KeyEvent.VK_ESCAPE;
        this.p1 = KeyEvent.VK_W;
        this.p2 = KeyEvent.VK_LEFT;
        this.start = KeyEvent.VK_ENTER;
    }

    public void draw(Graphics g){
        g.drawImage(portraits.get(player1).getImage(),0,500,null);
        g.drawImage(portraits.get(player2).getImage(),1920-portraits.get(player2).getIconWidth(),0,null);

        g.setColor(Color.BLACK);
        g.setFont(font);
        g.drawRect(0, 0, 1920, 1080);
        g.drawString("SELECT YOUR CHARACTER", 300, 200);

        g.drawString("Player 1", 1920 / 6, 400);
        g.drawImage(animations.get(player1).getImage(), 1920 / 4, 500, null);
        g.drawString(characters.get(player1), 1920 / 4, 800);

        g.drawString("Player 2", 1920 / 6 * 3 + 150, 400);
        g.drawImage(animations.get(player2).getImage(), 1920 / 6 * 3 + 300, 500, null);
        g.drawString(characters.get(player2), 1920 / 6 * 3 + 300, 800);

    }

    public void update(JComponent screen){
        long elapsedTime = System.currentTimeMillis() - currTime;
        currTime += elapsedTime;

        screen.requestFocus();

        for(Animation animation: animations){
            animation.update(elapsedTime);
        }
    }

    public void setPlayer1(){
        player1++;
        if(player1 >= characters.size()){
            player1 = 0;
        }
    }
    public void setPlayer2(){
        player2++;
        if(player2 >= characters.size()){
            player2 = 0;
        }
    }

    public boolean isDone(){
        return done;
    }

    public String getPlayer1(){
        return characters.get(player1);
    }
    public String getPlayer2(){
        return characters.get(player2);
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        int keyPressed = ke.getKeyCode();

        if (keyPressed == KeyEvent.VK_A) {
            this.setPlayer1();
        }
        if (keyPressed == p2) {
            this.setPlayer2();
        }
        if(keyPressed == exit){
            System.exit(0);
        }
        if(keyPressed == start){
            done = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {

    }

}
