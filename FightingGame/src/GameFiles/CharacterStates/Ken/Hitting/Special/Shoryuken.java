package GameFiles.CharacterStates.Ken.Hitting.Special;

import GameFiles.CharacterStates.Animations.HittingAnimation;
import GameFiles.Hitbox;
import GameFiles.Hurtbox;
import GameFiles.Sound;

public class Shoryuken extends SpecialMove {
    private static int damage = 50;
    private static int hbstart = 3;
    private static int hbend = 9;
    private boolean ended;
    private Hitbox hitbox;
    private Hurtbox hurtbox;
    private HittingAnimation animation;
    private static Sound sound = new Sound("Fighters/Ken/Shoryuken.wav");

    public Shoryuken(){
        this.hurtbox = new Hurtbox(0,0,30,30,damage,3);
        this.animation = new HittingAnimation("Ken", "Shoryuken",190,-280,50);
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
            hurtbox.updateHurtbox(x - 120, y+200+movesCharacterY());
        }
        else{
            hurtbox.updateHurtbox(x + 130, y+200+movesCharacterY());
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
        return 10;
    }
}
