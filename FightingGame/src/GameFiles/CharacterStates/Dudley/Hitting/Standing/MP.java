package GameFiles.CharacterStates.Dudley.Hitting.Standing;

import GameFiles.CharacterStates.Animations.HittingAnimation;
import GameFiles.CharacterStates.Dudley.Hitting.HittingMove;
import GameFiles.Hitbox;
import GameFiles.Hurtbox;

public class MP extends HittingMove {
    private static int damage = 20;
    private static int hbstart = 3;
    private static int hbend = 5;
    private boolean ended;
    private Hurtbox hurtbox;
    private HittingAnimation animation;

    public MP(){
        this.hurtbox = new Hurtbox(0,0,30,30,damage,1);
        this.animation = new HittingAnimation("Dudley", "MP",0,0,50);
        animation.start();
    }

    public Hurtbox getHurtbox() {
        if(animation.getCurrFrameIndex() >= hbstart && animation.getCurrFrameIndex() <= hbend){
            return hurtbox;
        }
        else{
            return null;
        }
    }
    public HittingAnimation getAnimation(){
        return animation;
    }

    public void updateMove(long elapsedTime, int x, int y, boolean facingLeft){
        animation.update(elapsedTime);
        if(animation.getCurrFrameIndex() == animation.getLength()-1){
            ended = true;
        }

        if(facingLeft) {
            hurtbox.updateHurtbox(x - 100, y+35);
        }
        else{
            hurtbox.updateHurtbox(x + 195, y+35);
        }
    }

    public void resetMove(){
        hurtbox.reset();
        animation.start();
        ended = false;
    }

    public int getDamage() {
        return damage;
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
        playSuperSound();
    }

    @Override
    public int leftOffset() {
        return 80;
    }
}
