package GameFiles.CharacterStates.Dudley;

import GameFiles.CharacterStates.Animations.HittingAnimation;
import GameFiles.Hitbox;
import GameFiles.Hurtbox;
import GameFiles.Sound;

public class Lose extends Move {
    private boolean ended;
    private HittingAnimation animation;
    private static Sound sound = new Sound("Fighters/Dudley/lose.wav");

    public Lose(){
        this.animation = new HittingAnimation("Dudley", "Lose",100,25,75);
        this.animation.start();
    }

    public Hurtbox getHurtbox() {
        return null;
    }


    public HittingAnimation getAnimation(){
        return animation;
    }

    public void updateMove(long elapsedTime, int x, int y, boolean facingLeft){
        if(animation.getCurrFrameIndex() != animation.getLength()-1) {
            this.animation.update(elapsedTime);
        }
    }

    public void resetMove(){
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
}
