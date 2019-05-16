package GameFiles.CharacterStates.Ken.Hitting;

import GameFiles.CharacterStates.Animations.HittingAnimation;
import GameFiles.Hitbox;
import GameFiles.Hurtbox;
import GameFiles.Sound;

public class Taunt extends HittingMove {
    private static int damage = 5;
    private static int hbstart = 3;
    private static int hbend = 10;
    private boolean ended;
    private Hurtbox hurtbox;
    private HittingAnimation animation;
    private static final Sound sound = new Sound("Fighters/Ken/intro.wav");

    public Taunt(){
        this.hurtbox = new Hurtbox(0,0,30,30,damage,2);
        this.animation = new HittingAnimation("Ken", "Starting",0,0,35);
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
            hurtbox.updateHurtbox(x - 60, y+20);
        }
        else{
            hurtbox.updateHurtbox(x + 170, y+20);
        }
    }

    public void resetMove(){
        hurtbox.reset();
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
        return 20;
    }
}
