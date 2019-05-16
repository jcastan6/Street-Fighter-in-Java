package GameFiles.CharacterStates.Ken.Hitting.Super;

import GameFiles.CharacterStates.Animations.HittingAnimation;
import GameFiles.Hitbox;
import GameFiles.Hurtbox;
import GameFiles.Sound;

public class SuperTatsu extends SuperMove {
    private static int damage = 50;
    private static int hbstart = 2;
    private static int hbend = 100;
    private boolean ended;
    private Hurtbox hurtbox;
    private HittingAnimation animation;
    private static Sound sound = new Sound("Fighters/Ken/superstart.wav");
    private static Sound sound2 = new Sound("Fighters/Ken/superkick.wav");

    public SuperTatsu(){
        this.hurtbox = new Hurtbox(0,0,50,50,damage,10);
        this.animation = new HittingAnimation("Ken", "SuperTatsu",180,-220,25);
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

        if(animation.getCurrFrameIndex() == 5 || animation.getCurrFrameIndex() == 17 || animation.getCurrFrameIndex() == 31 || animation.getCurrFrameIndex() == 48 || animation.getCurrFrameIndex() == 55 || animation.getCurrFrameIndex() == 65){
            sound2.run(0);
        }


        if(this.animation.getCurrFrameIndex() == this.animation.getLength()-1){
            this.ended = true;
        }
        if(animation.getCurrFrameIndex() <= 50) {
            if (facingLeft) {
                hurtbox.updateHurtbox(x - 80, y + 100);
            } else {
                hurtbox.updateHurtbox(x + 150, y + 100);
            }
        }
        else{
            if (facingLeft) {
                hurtbox.updateHurtbox(x - 100, y + 120);
            } else {
                hurtbox.updateHurtbox(x + 160, y + 120);
            }
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
        if(animation.getCurrFrameIndex() <= 85){
            return 10;
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
        return 1;
    }
}
