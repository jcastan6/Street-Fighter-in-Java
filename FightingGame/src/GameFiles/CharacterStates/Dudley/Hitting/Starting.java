package GameFiles.CharacterStates.Dudley.Hitting;

import GameFiles.CharacterStates.Animations.HittingAnimation;
import GameFiles.Hitbox;
import GameFiles.Hurtbox;
import GameFiles.Sound;

public class Starting extends HittingMove {
    private boolean ended;

    private HittingAnimation animation;
    private static final Sound sound = new Sound("Fighters/Dudley/intro.wav");

    public Starting(){
        this.animation = new HittingAnimation("Dudley", "Starting",100,-185,50);
    }

    public Hurtbox getHurtbox() {
        return null;
    }

    public HittingAnimation getAnimation(){
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
    }

    @Override
    public int leftOffset() {
        return -95;
    }
}
