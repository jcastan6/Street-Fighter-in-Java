package GameFiles.CharacterStates.Ken.Hit;

import GameFiles.CharacterStates.Animations.Animation;
import GameFiles.CharacterStates.Animations.StandbyAnimation;
import GameFiles.Hitbox;
import GameFiles.Hurtbox;
import GameFiles.Sound;

public class CrouchHit extends HitMove {
    private static Sound sound = new Sound("Fighters/Ken/hit.wav");
    private static Sound sound2 = new Sound("Fighters/Ken/punch.wav");
    private boolean ended;
    private StandbyAnimation animation;

    public CrouchHit(){
        this.animation = new StandbyAnimation("Ken", "CrouchHit", 20,70,50);
        animation.start();
    }

    public Hurtbox getHurtbox() {
        return null;
    }


    public Animation getAnimation(){
        return animation;
    }

    public void updateMove(long elapsedTime, int x, int y, boolean facingLeft){
        animation.update(elapsedTime);
        if(animation.getCurrFrameIndex() == animation.getLength()-1){
            ended = true;
        }
    }

    public void resetMove(){
        animation.start();
        ended = false;
    }

    public int getDamage() {
        return 0;
    }

    public int movesCharacterX(){
        return 0;
    }

    public int movesCharacterY(){
        return 0;
    }

    @Override
    public boolean hasEnded() {
        return ended;
    }

    @Override
    public void playSound() {
        sound.run(0);
        sound2.run(0);
    }

    @Override
    public boolean allowsHit() {
        return false;
    }
}
