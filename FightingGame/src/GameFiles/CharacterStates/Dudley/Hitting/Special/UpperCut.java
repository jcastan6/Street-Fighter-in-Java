package GameFiles.CharacterStates.Dudley.Hitting.Special;

import GameFiles.CharacterStates.Animations.HittingAnimation;
import GameFiles.Hitbox;
import GameFiles.Hurtbox;
import GameFiles.Sound;

public class UpperCut extends SpecialMove {
    private static int damage = 70;
    private static int hbstart = 3;
    private static int hbend = 9;
    private int movingY = 0;
    private boolean ended;
    private Hurtbox hurtbox;
    private HittingAnimation animation;
    private static Sound sound = new Sound("Fighters/Dudley/uppercut.wav");

    public UpperCut(){
        this.hurtbox = new Hurtbox(0,0,60,30,damage,3);
        this.animation = new HittingAnimation("Dudley", "Uppercut",100,-150,50);
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
            hurtbox.updateHurtbox(x - 120, y+230+movesCharacterY());
        }
        else{
            hurtbox.updateHurtbox(x + 130, y+230+movesCharacterY());
        }
    }

    public void resetMove(){
        hurtbox.reset();
        animation.start();
        ended = false;
        movingY = 0;
    }

    public int getDamage() {
        return damage;
    }

    public int movesCharacterX(){
        return 1;
    }

    public int movesCharacterY(){
        if(animation.getCurrFrameIndex() < 12) {
            return -2;
        }
        else{
            return 2;
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

    @Override
    public int leftOffset() {
        return 0;
    }
}
