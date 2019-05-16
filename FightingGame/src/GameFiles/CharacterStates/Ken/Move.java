package GameFiles.CharacterStates.Ken;

import GameFiles.CharacterStates.Animations.Animation;
import GameFiles.Hitbox;
import GameFiles.Hurtbox;
import GameFiles.Sound;

public abstract class Move{
    private Sound KickSound = new Sound("Fighters/Ken/kick.wav");
    public void playSuperSound(){
        KickSound.run(0);
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
