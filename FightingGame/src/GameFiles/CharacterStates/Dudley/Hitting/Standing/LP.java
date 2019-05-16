package GameFiles.CharacterStates.Dudley.Hitting.Standing;

import GameFiles.CharacterStates.Animations.HittingAnimation;
import GameFiles.CharacterStates.Dudley.Hitting.HittingMove;
import GameFiles.Hitbox;
import GameFiles.Hurtbox;

public class LP extends HittingMove {
    private static int damage = 10;
    private static int hbstart = 2;
    private static int hbend = 4;
    private boolean ended;
    private Hurtbox hurtbox;
    private HittingAnimation animation;

    public LP(){
        this.hurtbox = new Hurtbox(0,0,30,30,damage,1);
        this.animation = new HittingAnimation("Dudley", "LP",0,0,50);
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
        this.animation.update(elapsedTime);
        if(this.animation.getCurrFrameIndex() == this.animation.getLength()-1){
            this.ended = true;
        }
        if(facingLeft) {
            hurtbox.updateHurtbox(x - 110, y + 25);
        }
        else{
            hurtbox.updateHurtbox(x + 190, y + 25);
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
    public void playSound(){playSuperSound();
    }

    @Override
    public int leftOffset() {
        return 75;
    }
}
