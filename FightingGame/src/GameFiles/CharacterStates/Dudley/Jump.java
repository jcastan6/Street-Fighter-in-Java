package GameFiles.CharacterStates.Dudley;

import GameFiles.CharacterStates.Animations.Animation;
import GameFiles.CharacterStates.Animations.StandbyAnimation;
import GameFiles.Hitbox;
import GameFiles.Hurtbox;

public class Jump extends Move {
    private boolean ended;
    private StandbyAnimation animation;

    public Jump(){
        this.animation = new StandbyAnimation("Dudley", "Jump",0,-120,25);
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
        if(animation.getCurrFrameIndex() < 13){
            return -11;
        }
        else{
            return 8;
        }
    }

    @Override
    public boolean hasEnded() {
        return ended;
    }

    @Override
    public void playSound() {

    }
}
