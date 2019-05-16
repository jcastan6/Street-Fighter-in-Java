package GameFiles.Characters;
import GameFiles.Collisionable;
import GameFiles.Hitbox;
import GameFiles.Hurtbox;
import GameFiles.Sound;

import java.awt.*;

public abstract class GameCharacter implements Collisionable {
    private Sound superSound = new Sound("/letsgo.wav");
    private Sound loseSound = new Sound("/finalhit.wav");
    public void playSuperSound(){
        superSound.run(0);
    }
    public void playLoseSound(){
        loseSound.run(0);
    }

    public abstract void toggleDownPressed();

    public abstract void toggleRightPressed();

    public abstract void toggleLeftPressed();

    public abstract void TogglePunchPressed();

    public abstract void unToggleDownPressed();

    public abstract void unToggleRightPressed();

    public abstract void unToggleLeftPressed();

    public abstract void unTogglePunchPressed();

    public abstract Hitbox getHitbox();

    public abstract void hit(Rectangle collision, int side, int damage);

    public abstract void drawImage(Graphics g);

    public abstract int getX();

    public abstract int getY();

    public abstract void setCanMove(int move);

    @Override
    public abstract void handleCollision(Collisionable object);

    public abstract boolean getFacingLeft();

    public abstract void setFacingleft(Boolean facing);

    public abstract boolean inMove();

    public abstract int getHealth();

    public abstract void update();

    public abstract void activateMove(String move);

    public abstract void activateSpecialMove(String move);

    public abstract void activateSuperMove(String move);

    public abstract void toggleDash(boolean forwards);

    public abstract int getSuperbar();

    public abstract void start();

    public abstract boolean hasWon();

    public abstract boolean died();

    public abstract void gameoverupdate();

    public abstract String getName();

}