package GameFiles.CharacterStates.Dudley.Hitting;

import GameFiles.CharacterStates.Animations.HittingAnimation;
import GameFiles.Hitbox;
import GameFiles.Hurtbox;
import GameFiles.Sound;

public class Taunt extends HittingMove {
    private static int damage = 5;
    private static int hbstart = 1;
    private static int hbend = 23;
    private int movingX;
    private int movingY;
    private boolean ended;
    private Hurtbox hurtbox;
    private HittingAnimation animation;
    private static final Sound sound = new Sound("Fighters/Dudley/taunt.wav");

    public Taunt(){
        this.hurtbox = new Hurtbox(0,0,30,30,damage,2);
        this.animation = new HittingAnimation("Dudley", "Taunt",18,-100,50);
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

        if(animation.getCurrFrameIndex() < 18) {
            movingX += 4;
        }

        if(animation.getCurrFrameIndex() > 1 && animation.getCurrFrameIndex() <12) {
            movingY -= 1;
        }
        else{
            movingY += 8;
        }

        if(facingLeft) {
            hurtbox.updateHurtbox(x - 100 - movingX, y-100+movingY);
        }
        else{
            hurtbox.updateHurtbox(x + 160 + movingX, y-100+movingY);
        }
    }

    public void resetMove(){
        movingX = 0;
        movingY = 0;
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
        return 270;
    }
}
