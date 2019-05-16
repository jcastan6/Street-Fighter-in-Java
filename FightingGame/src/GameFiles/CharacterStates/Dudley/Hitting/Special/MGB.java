package GameFiles.CharacterStates.Dudley.Hitting.Special;

import GameFiles.CharacterStates.Animations.HittingAnimation;
import GameFiles.Hitbox;
import GameFiles.Hurtbox;
import GameFiles.Sound;

public class MGB extends SpecialMove {
    private static int damage = 30;
    private static int hbstart = 1;
    private static int hbend = 16;
    private boolean ended;
    private Hurtbox hurtbox;
    private HittingAnimation animation;
    private static Sound sound = new Sound("Fighters/Dudley/mgb.wav");

    public MGB(){
        this.hurtbox = new Hurtbox(0,0,70,30,damage,3);
        this.animation = new HittingAnimation("Dudley", "MGB",20,-10,45);
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
            hurtbox.updateHurtbox(x - 95, y+60+movesCharacterY());
        }
        else{
            hurtbox.updateHurtbox(x + 150, y+60+movesCharacterY());
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
        if(animation.getCurrFrameIndex() <= 9){
            return 20;
        }
        else{
            return 0;
        }
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
