package GameFiles.CharacterStates.Ken.Hitting.Special;

import GameFiles.CharacterStates.Animations.HittingAnimation;
import GameFiles.Hitbox;
import GameFiles.Hurtbox;
import GameFiles.Sound;

public class MTatsu extends SpecialMove {
    private static int damage = 20;
    private static int hbstart = 2;
    private static int hbend = 14;
    private boolean ended;
    private Hurtbox hurtbox;
    private HittingAnimation animation;
    private static Sound sound = new Sound("Fighters/Ken/tatsu.wav");

    public MTatsu(){
        this.hurtbox = new Hurtbox(0,0,30,30,damage,10);
        this.animation = new HittingAnimation("Ken", "MTatsu",60,-60,50);
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
            hurtbox.updateHurtbox(x - 80, y + 30);
        }
        else{
            hurtbox.updateHurtbox(x + 180, y + 30);
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
        return 2;
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
        return 0;
    }
}
