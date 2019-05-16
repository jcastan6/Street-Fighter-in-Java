package GameFiles.Characters;

import GameFiles.*;
import GameFiles.CharacterStates.Animations.Animation;
import GameFiles.CharacterStates.Animations.StandbyAnimation;
import GameFiles.CharacterStates.Ken.*;
import GameFiles.CharacterStates.Ken.Hit.CrouchHit;
import GameFiles.CharacterStates.Ken.Hit.Hit;
import GameFiles.CharacterStates.Ken.Hit.HitMove;
import GameFiles.CharacterStates.Ken.Hit.Knockback;
import GameFiles.CharacterStates.Ken.Hitting.HittingMove;
import GameFiles.CharacterStates.Ken.Hitting.Special.*;
import GameFiles.CharacterStates.Ken.Hitting.Standing.*;
import GameFiles.CharacterStates.Ken.Hitting.Taunt;

import GameFiles.CharacterStates.Ken.Hitting.Super.SuperMove;
import GameFiles.CharacterStates.Ken.Hitting.Super.SuperTatsu;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Ken extends GameCharacter{
    private int x;
    private int y;
    private final int DEFWIDTH = 736;
    private final int DEFHEIGHT = 476;
    private final int XCENTER = DEFWIDTH/2-55;
    private final int YCENTER = DEFHEIGHT/2-80;
    private final int vx = 8;
    private int yfloor;
    private int health;
    private int superbar;
    private final int superlimit = 300;

    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean hitStun;

    private int canMove;
    private long currTime;

    private Move currentMove = null;

    private Hurtbox currentHurtbox;
    private Hitbox currentHitbox;
    private Hitbox standingHitbox;
    private Hitbox crouchHitbox;

    private Animation currentAnimation;
    private Animation standingAnimation;
    private Animation walkingAnimation;
    private Animation backWalkingAnimation;
    private Animation crouchAnimation;
    private Projectile projectile = null;

    private ArrayList<Animation> animations;

    private Boolean facingleft;
    private Boolean blocking;
    private Boolean inMove;
    private boolean PunchPressed;

    private HashMap<String, Move> moves = new HashMap<>();
    {
        moves.put("LP", new LP());
        moves.put("MP", new MP());
        moves.put("HP", new HP());
        moves.put("LK", new LK());
        moves.put("MK", new MK());
        moves.put("HK", new HK());
        moves.put("Shoryuken", new Shoryuken());
        moves.put("Hadoken", new Hadoken());
        moves.put("LTatsu", new LTatsu());
        moves.put("MTatsu", new MTatsu());
        moves.put("HTatsu", new HTatsu());
        moves.put("Taunt", new Taunt());
        moves.put("Block", new Block());
        moves.put("Hit", new Hit());
        moves.put("CrouchHit", new CrouchHit());
        moves.put("Jump", new Jump());
        moves.put("DashF", new DashF());
        moves.put("DashB", new DashB());
        moves.put("Knockback", new Knockback());
        moves.put("SuperTatsu", new SuperTatsu());
        moves.put("Win", new Win());
        moves.put("Lose", new Lose());
    }

    public Ken(int x, int y, boolean facingleft) {
        String character = "Ken";
        this.x = x;
        this.y = y;
        this.yfloor = y;
        this.health = 800;
        this.superbar = 0;
        this.facingleft = facingleft;

        canMove = 0;

        animations = new ArrayList<>();
        this.standingAnimation = new StandbyAnimation(character, "Standing",0,0,50);
        this.backWalkingAnimation = new StandbyAnimation(character, "WalkingB",20,0,50);
        this.walkingAnimation = new StandbyAnimation(character, "WalkingF",20,0,50);
        this.crouchAnimation = new StandbyAnimation(character, "Crouch",0,80,80);

        animations.add(standingAnimation);
        animations.add(backWalkingAnimation);
        animations.add(walkingAnimation);
        animations.add(crouchAnimation);

        this.standingHitbox = new Hitbox(x+XCENTER,y+YCENTER, 100,225);
        this.crouchHitbox = new Hitbox(x, y, 125,150);

        hitStun = false;
        currentAnimation = this.standingAnimation;
        currentHitbox = standingHitbox;

        this.inMove = false;

        blocking = false;
        //parryCounter = 0;
    }

    public void toggleDownPressed() {
        if(currentMove == null){
            unToggleRightPressed();
            unToggleLeftPressed();
            currentHitbox = crouchHitbox;
            currentAnimation = crouchAnimation;
        }
        this.DownPressed = true;
    }

    public void toggleRightPressed() {
        if(currentMove == null){
            unToggleDownPressed();
            if(facingleft) {
                currentAnimation = backWalkingAnimation;
                blocking = true;
            }
            else{
                currentAnimation = walkingAnimation;
            }
            this.RightPressed = true;
        }
    }

    public void toggleLeftPressed() {
        if(currentMove == null) {
            unToggleDownPressed();
            if(facingleft) {
                currentAnimation = walkingAnimation;
            }
            else{
                currentAnimation = backWalkingAnimation;
                blocking = true;
            }
            this.LeftPressed = true;
        }
    }

    public void toggleHadoken(){
        activateSpecialMove("Hadoken");
        if (projectile == null && currentMove instanceof Hadoken) {
            currentHurtbox = null;
            if (facingleft) {
                this.projectile = new Projectile(x + XCENTER + 70, y + YCENTER + 30, -13, 0, true);
            } else {
                this.projectile = new Projectile(x + XCENTER + 30, y + YCENTER + 30, 13, 0, false);
            }
        }
    }

    public void TogglePunchPressed(){
        this.PunchPressed = true;
    }

    public void unToggleDownPressed() {
        if(currentMove == null){
            currentHitbox = standingHitbox;
            currentAnimation = standingAnimation;
        }
        this.DownPressed = false;
    }

    public void unToggleRightPressed() {
        if(currentMove == null || DownPressed) {
            currentAnimation = standingAnimation;
        }
        this.RightPressed = false;
        blocking = false;
    }

    public void unToggleLeftPressed() {
        if(currentMove == null || DownPressed) {
            currentAnimation = standingAnimation;
        }
        this.LeftPressed = false;
        blocking = false;
    }

    public void unTogglePunchPressed(){
        this.PunchPressed = false;
    }

    private void moveLeft() {
            if(canMove==1 || canMove == 0) {
                x -= vx;
            }

    }
    private void moveRight() {

            if(canMove == 2 || canMove == 0) {
                x += vx;
            }
        }

    public Hitbox getHitbox(){
        return currentHitbox;
    }


    public Hurtbox getCurrentHurtbox() {
        if(currentHurtbox == null){
            return null;
        }
        else{
            return currentHurtbox;
        }
    }

    private void collision(Rectangle collision, int side) {
        if(!hitStun) {
            if (side == 1) {
                if(canMove==1 || canMove == 0) {
                    x -= collision.width - 1;
                }
            }
            if (side == 3) {
                if(canMove==2 || canMove == 0) {
                    x += collision.width + 1;
                }
            }
            standingHitbox.updateHitBox(x + XCENTER, y + YCENTER);
        }
    }

    public void hit(Rectangle collision, int side, int damage){
        if(blocking && !hitStun){
            hitStun = true;
            health -= damage/5;
            activateMove("Block");
        }

        else if(!hitStun || (currentMove instanceof HitMove && ((HitMove) currentMove).allowsHit()) && !(currentMove instanceof SuperMove)){
            hitStun = true;
            health -= damage;

            if(currentMove instanceof HitMove || damage >= 30 || side == 4){
                ovverrideMove("Knockback");
            }
            else if(currentAnimation == crouchAnimation){
                ovverrideMove("CrouchHit");
            }
            else {
                ovverrideMove("Hit");
            }

            if(facingleft) {
                if(canMove==2 || canMove == 0) {
                    x += damage * 2;
                }
                if (side == 4) {
                    y -= damage*3;
                }
            }
            else{
                if(canMove==1 || canMove == 0) {
                    x -= damage*2;
                }
                if (side == 4) {
                    y-= damage*3;
                }
            }
        }
    }

    public void drawImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        if(GameWorld.debug) {
            if (currentHitbox != null) {
                currentHitbox.drawBox(g);
            }
            if (currentHurtbox != null) {
                currentHurtbox.drawBox(g);
            }

        }

        if (projectile != null) {
            projectile.drawImage(g);
        }

        if(!(currentMove instanceof Win || currentMove instanceof Lose)) {
            g.setColor(new Color(0f, 0f, 0f, .5f));
            g.fillOval(x + 270 + (yfloor - y) / 5, yfloor + 360, 170 - (yfloor - y) / 5, 30);
        }

        if(facingleft) {
            if(currentMove instanceof HittingMove) {
                g2d.drawImage(currentAnimation.getImage(), x + currentAnimation.getImage().getWidth(null) + 275 - (currentAnimation.getOffsetX()) - ((HittingMove) currentMove).leftOffset(), y + 160 + currentAnimation.getOffsetY(), -currentAnimation.getImage().getWidth(null), currentAnimation.getImage().getHeight(null), null);
            }
            else{
                g2d.drawImage(currentAnimation.getImage(), x + currentAnimation.getImage().getWidth(null) + 275 - (currentAnimation.getOffsetX()), y + 160 + currentAnimation.getOffsetY(), -currentAnimation.getImage().getWidth(null), currentAnimation.getImage().getHeight(null), null);
            }
        }
        else{
            g2d.drawImage(currentAnimation.getImage(), x+275-currentAnimation.getOffsetX(), y+160 +currentAnimation.getOffsetY(), null);
        }
    }

    public int getX(){
        return getHitbox().getBox().x;
    }
    public int getY(){
        return getHitbox().getBox().y + getHitbox().getBox().height;
    }

    public void setCanMove(int move){
        canMove = move;
    }

    @Override
    public void handleCollision(Collisionable object) {
        //moving collision
        Rectangle charbox = currentHitbox.getBox();
        Rectangle charbox2 = object.getHitbox().getBox();
        if(charbox.intersects(charbox2)){
            Rectangle intersection = charbox.intersection(charbox2);
            if(intersection.height > intersection.width  && charbox.x < intersection.x){
                collision(charbox.intersection(charbox2),1);
            }
            else if(intersection.height > intersection.width  && charbox.x > charbox2.x){
                collision(charbox.intersection(charbox2),3);
            }
            else if(intersection.height < intersection.width  && charbox.y < intersection.y){
                collision(charbox.intersection(charbox2),2);
            }
            else if(intersection.height < intersection.width  && charbox.y > charbox2.y){
                collision(charbox.intersection(charbox2),4);
            }

        }

        //attack collision
        if(currentMove != null && currentMove.getHurtbox()!= null) {
            Rectangle movebox = currentMove.getHurtbox().getBox();
            charbox2 = object.getHitbox().getBox();
            if (movebox.intersects(charbox2)) {
                superbar += 1;
                Rectangle intersection = movebox.intersection(charbox2);
                int damage = currentMove.getHurtbox().getDamage();

                if(facingleft){
                    this.x+=damage/2.5;
                }
                else {
                    this.x-=damage/2.5;
                }

                if (movebox.y > charbox2.y+charbox2.height-50) {
                    object.hit(charbox.intersection(charbox2), 4,damage);
                }
                else if (movebox.x < intersection.x) {
                    object.hit(movebox.intersection(charbox2), 1,damage);
                } else if (movebox.x > charbox2.x) {
                    object.hit(movebox.intersection(charbox2), 3,damage);
                }

                if(superbar %100 == 0){
                    super.playSuperSound();
                }
            }
        }

        //projectile collision, if projectile exists
        if(this.projectile != null) {
            charbox = projectile.getHurtbox().getBox();
            charbox2 = object.getHitbox().getBox();
            if (charbox.intersects(charbox2)) {
                int projdamage = projectile.getHurtbox().getDamage();
                projectile = null;
                Rectangle intersection = charbox.intersection(charbox2);
                if (charbox.x < intersection.x) {
                    object.hit(charbox.intersection(charbox2), 1,projdamage);
                } else if (charbox.x > charbox2.x) {
                    object.hit(charbox.intersection(charbox2), 3,projdamage);
                } else if (charbox.y < intersection.y) {
                    object.hit(charbox.intersection(charbox2), 2,projdamage);
                } else if (charbox.y > charbox2.y) {
                    object.hit(charbox.intersection(charbox2), 4,projdamage);
                }
            }
        }
    }

    public boolean getFacingLeft(){
        return this.facingleft;
    }

    public void setFacingleft(Boolean facing){
        facingleft = facing;
    }

    public boolean inMove(){return  inMove;}

    public int getHealth(){
        return health/8;
    }

    private void Punch(){
        this.PunchPressed = true;
        this.LeftPressed = false;
        this.RightPressed = false;
    }

    public synchronized void update() {
        if(currentMove == null && y < yfloor){
            y = yfloor;
        }

        if(superbar >= superlimit){
            superbar = superlimit;
        }

        if(projectile != null){
            projectile.update();
            if(projectile.getHurtbox().getBox().x > x+XCENTER+700 || projectile.getHurtbox().getBox().x < x-700){
                projectile = null;
            }
        }

        long elapsedTime = System.currentTimeMillis() - currTime;
        currTime += elapsedTime;

        if(currentMove != null){
            if(currentMove instanceof Jump || currentMove instanceof SuperMove){
                currentHitbox = crouchHitbox;
            }

            currentMove.updateMove(elapsedTime, x+XCENTER,y+YCENTER,facingleft);
            currentAnimation = currentMove.getAnimation();
            currentHurtbox = currentMove.getHurtbox();

            if(currentMove.movesCharacterX() != 0 && canMove == 0){
                if (facingleft) {
                    x -= currentMove.movesCharacterX();
                } else {
                    x += currentMove.movesCharacterX();
                }
            }

            if(currentMove.movesCharacterY() != 0){
                y += currentMove.movesCharacterY();
                if(y >= yfloor){
                    y = yfloor;
                }
            }
            standingHitbox.updateHitBox(x + XCENTER, y + YCENTER+currentAnimation.getOffsetY()/2);
            crouchHitbox.updateHitBox(x + XCENTER, y + YCENTER + 75 + currentAnimation.getOffsetY()/2);

            if(currentMove.hasEnded()){
                if(GameWorld.debug){
                    health = 800;
                    superbar = superlimit;
                }

                hitStun = false;
                currentMove.resetMove();
                currentMove = null;
                currentHurtbox = null;
                if(this.DownPressed){
                    currentHitbox = crouchHitbox;
                    currentAnimation = crouchAnimation;
                }
                else {
                    currentHitbox = standingHitbox;
                    currentAnimation = standingAnimation;
                }
            }
        }

        else {
            crouchHitbox.updateHitBox(x + XCENTER, y + YCENTER + 75);
            standingHitbox.updateHitBox(x + XCENTER, y + YCENTER);
            currentAnimation.update(elapsedTime);
        }

        if (this.LeftPressed) {
            blocking = !facingleft;
            this.moveLeft();
        }
        if (this.RightPressed) {
            blocking = facingleft;
            this.moveRight();
        }
        if(this.RightPressed && this.LeftPressed){
            currentAnimation = standingAnimation;
        }
    }

    public void activateMove(String move){
        if(currentMove == null) {
            currentMove = moves.get(move);
            currentMove.playSound();
            Punch();
        }
    }

    private void ovverrideMove(String move){
        if(currentMove != null) {
            currentMove.resetMove();
        }
        currentMove = moves.get(move);
        currentMove.playSound();
        Punch();
    }

    public void activateSpecialMove(String move){
        if(currentMove != null && (!(currentMove instanceof Jump)) && (!(currentMove instanceof SpecialMove))){
            if (currentMove != null) {
                currentMove.resetMove();
            }
            currentMove = moves.get(move);
            currentMove.playSound();
            Punch();
        }
    }

    public void activateSuperMove(String move){
        if(superbar >= ((SuperMove) moves.get(move)).cost()*100) {
            if (currentMove != null) {
                currentMove.resetMove();
            }
            currentMove = moves.get(move);
            superbar -= ((SuperMove) currentMove).cost() * 100;
            currentMove.playSound();
            Punch();
        }
    }

    public void toggleDash(boolean forwards) {
        if(forwards){
            activateMove("DashF");
        }
        else{
            activateMove("DashB");
        }

    }

    public int getSuperbar(){
        return superbar;
    }

    public void start(){
        ovverrideMove("Taunt");
    }

    public boolean hasWon(){
        if(currentMove != null){
            return false;
        }
        else{
            y = yfloor;
            activateMove("Win");
            return true;
        }

    }

    public boolean died(){
        if(currentMove instanceof Lose){
            return true;
        }
        else if(health <= 0){
            y = yfloor;
            super.playLoseSound();
            ovverrideMove("Lose");
            return true;
        }

        else{
            return false;
        }
    }

    public void gameoverupdate(){
        long elapsedTime = System.currentTimeMillis() - currTime;
        currTime += elapsedTime;

        currentMove.updateMove(elapsedTime,x+XCENTER,y+YCENTER, facingleft);
        currentAnimation = currentMove.getAnimation();
    }

    public String getName(){
        return "Ken";
    }
}
