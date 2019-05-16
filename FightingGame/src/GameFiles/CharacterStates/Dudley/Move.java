package GameFiles.CharacterStates.Dudley;

import GameFiles.CharacterStates.Animations.Animation;
import GameFiles.Hitbox;
import GameFiles.Hurtbox;
import GameFiles.Sound;

import java.util.Random;

public abstract class Move{
    private Sound punchSound = new Sound("Fighters/Dudley/punch.wav");
    private Sound punchSound2 = new Sound("Fighters/Dudley/punch2.wav");
    private Sound punchSound3 = new Sound("Fighters/Dudley/punch3.wav");

    private Sound[] sounds = {punchSound,punchSound2,punchSound3};
    public void playSuperSound(){
        Random r = new Random();
        sounds[r.nextInt(sounds.length)].run(0);
    }

    public abstract Animation getAnimation();
    public abstract Hurtbox getHurtbox();
    public abstract int getDamage();
    public abstract void updateMove(long elapsedTime, int x, int y, boolean facingLeft);
    public abstract void resetMove();
    public abstract int movesCharacterX();
    public abstract int movesCharacterY();
    public abstract boolean hasEnded();
    public abstract void playSound();

}
