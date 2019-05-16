package GameFiles.CharacterStates.Dudley;

import GameFiles.CharacterStates.Animations.Animation;
import GameFiles.CharacterStates.Animations.DashingAnimation;
import GameFiles.Hitbox;
import GameFiles.Hurtbox;

public class DashB extends Move {
    private boolean ended;
    private DashingAnimation animation;

    public DashB(){
        this.animation = new DashingAnimation("Dudley", "DashB",10,-5,35);
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
        return -5;
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
    }
}
