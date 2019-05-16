package GameFiles.CharacterStates.Ken.Hit;

import GameFiles.CharacterStates.Animations.Animation;
import GameFiles.CharacterStates.Animations.StandbyAnimation;
import GameFiles.Hitbox;
import GameFiles.Hurtbox;
import GameFiles.Sound;

public class Knockback extends HitMove {
    private static Sound sound = new Sound("Fighters/Ken/hit.wav");
    private static Sound sound2 = new Sound("Fighters/Ken/punch.wav");
    private boolean ended;
    private boolean hit;
    private StandbyAnimation animation;

    public Knockback(){
        this.animation = new StandbyAnimation("Ken", "Knockback",50,-50,45);
        animation.start();
        hit = false;
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
        hit = false;
        animation.start();
        ended = false;
    }

    public int getDamage() {
        return 0;
    }

    public int movesCharacterX(){
        if(animation.getCurrFrameIndex() < 10) {
            return -4;
        }
        else {
            return 0;
        }
    }

    public int movesCharacterY(){
        if(animation.getCurrFrameIndex() < 7 ) {
            return -2;
        }
        else{
            return 6;
        }
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
        if(animation.getCurrFrameIndex() > 8 && animation.getCurrFrameIndex() < 10){
            return true;
        }
        else {
            return false;
        }
    }
}
