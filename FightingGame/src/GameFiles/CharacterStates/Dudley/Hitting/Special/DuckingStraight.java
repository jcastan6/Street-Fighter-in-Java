package GameFiles.CharacterStates.Dudley.Hitting.Special;

import GameFiles.CharacterStates.Animations.HittingAnimation;
import GameFiles.Hitbox;
import GameFiles.Hurtbox;
import GameFiles.Sound;

public class DuckingStraight extends SpecialMove {
    private static int damage = 60;
    private static int hbstart = 10;
    private static int hbend = 15;
    private boolean ended;
    private Hurtbox hurtbox;
    private HittingAnimation animation;
    private static Sound sound = new Sound("Fighters/Dudley/DuckingStraight.wav");

    public DuckingStraight(){
        this.hurtbox = new Hurtbox(0,0,100,30,damage,3);
        this.animation = new HittingAnimation("Dudley", "DuckingStraight",95,-10,45);
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
            hurtbox.updateHurtbox(x - 135, y+50);
        }
        else{
            hurtbox.updateHurtbox(x + 170, y+50);
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
        return 1;
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
