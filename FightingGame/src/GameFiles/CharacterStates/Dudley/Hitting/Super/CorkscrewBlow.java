package GameFiles.CharacterStates.Dudley.Hitting.Super;

import GameFiles.CharacterStates.Animations.HittingAnimation;
import GameFiles.Hitbox;
import GameFiles.Hurtbox;
import GameFiles.Sound;

public class CorkscrewBlow extends SuperMove {
    private static int damage = 120;
    private static int hbstart = 13;
    private static int hbend = 24;
    private boolean ended;
    private Hurtbox hurtbox;
    private HittingAnimation animation;
    private static Sound sound = new Sound("Fighters/Dudley/corkscrew.wav");

    public CorkscrewBlow(){
        this.hurtbox = new Hurtbox(0,0,120,50,damage,10);
        this.animation = new HittingAnimation("Dudley", "CorkscrewBlow",50,-20,25);
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

        if (facingLeft) {
            hurtbox.updateHurtbox(x - 80, y + 50);
        } else {
            hurtbox.updateHurtbox(x + 100, y + 50);
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
        if(animation.getCurrFrameIndex() <= 23){
            return 7;
        }
        else{
            return 0;
        }
    }

    public int movesCharacterY(){
        if(animation.getCurrFrameIndex() <= 50){
            return 0;
        }
        if(animation.getCurrFrameIndex() > 50 && animation.getCurrFrameIndex() <= 90){
            return -1;
        }
        else{
            return 4;
        }
    }

    @Override
    public boolean hasEnded() {
        return ended;
    }

    @Override
    public void playSound() {
        sound.run(0);
    }

    public int leftOffset() {
        return -25;
    }

    @Override
    public int cost() {
        return 2;
    }
}
