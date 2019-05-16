package GameFiles.CharacterStates.Dudley.Hitting.Special;

import GameFiles.CharacterStates.Animations.HittingAnimation;
import GameFiles.Hitbox;
import GameFiles.Hurtbox;

public class Counter extends SpecialMove {
    private boolean ended;
    private HittingAnimation animation;

    public Counter(){
        this.animation = new HittingAnimation("Dudley", "Counter",10,-45,60);
        this.animation.start();
    }

    public Hurtbox getHurtbox() {
        return null;
    }

    public HittingAnimation getAnimation(){
        return animation;
    }

    public void updateMove(long elapsedTime, int x, int y, boolean facingLeft){
        this.animation.update(elapsedTime);
        if(this.animation.getCurrFrameIndex() == this.animation.getLength()-1){
            this.ended = true;
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
    }

    @Override
    public int leftOffset() {
        return 0;
    }
}
