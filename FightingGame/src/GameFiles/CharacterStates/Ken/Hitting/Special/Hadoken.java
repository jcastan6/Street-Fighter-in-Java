package GameFiles.CharacterStates.Ken.Hitting.Special;

import GameFiles.CharacterStates.Animations.HittingAnimation;
import GameFiles.Hitbox;
import GameFiles.Hurtbox;
import GameFiles.Sound;

public class Hadoken extends SpecialMove {
    private static int damage = 20;
    private static int hbstart = 2;
    private static int hbend = 4;
    private static Sound sound = new Sound("Fighters/Ken/hadoken.wav");
    private boolean ended;
    private Hitbox hitbox;
    private HittingAnimation animation;

    public Hadoken(){
        this.animation = new HittingAnimation("Ken", "Hadoken",40,0,50);
        animation.start();
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
        sound.run(0);
    }

    @Override
    public int leftOffset() {
        return 0;
    }
}
