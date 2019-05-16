package GameFiles.CharacterStates.Dudley.Hitting.Special;

import GameFiles.CharacterStates.Animations.HittingAnimation;
import GameFiles.Hitbox;
import GameFiles.Hurtbox;
import GameFiles.Sound;

public class CrossCounter extends SpecialMove {
    private static int damage = 60;
    private static int hbstart = 22;
    private static int hbend = 30;
    private boolean ended;
    private Hurtbox hurtbox;
    private HittingAnimation animation;
    private static Sound sound = new Sound("Fighters/Dudley/crosscounter.wav");

    public CrossCounter(){
        this.hurtbox = new Hurtbox(0,0,200,30,damage,3);
        this.animation = new HittingAnimation("Dudley", "CrossCounter",75,-45,25);
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
            hurtbox.updateHurtbox(x - 45, y+60+movesCharacterY());
        }
        else{
            hurtbox.updateHurtbox(x + 100, y+60+movesCharacterY());
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
        return 5;
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
        return 65;
    }
}
