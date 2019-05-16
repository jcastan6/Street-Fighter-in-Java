/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameFiles;


import GameFiles.Characters.GameCharacter;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardControl implements KeyListener {

    private GameCharacter character;
    private final int up;
    private final int down;
    private final int right;
    private final int left;

    private final int LP;
    private final int MP;
    private final int HP;

    private final int LK;
    private final int MK;
    private final int HK;

    private MoveBuffer buffer;

    public KeyboardControl(GameCharacter character,MoveBuffer buffer, int up, int down, int left, int right, int LP, int MP, int HP, int LK, int MK, int HK) {
        this.character = character;
        this.up = up;
        this.down = down;
        this.right = right;
        this.left = left;

        this.LP = LP;
        this.MP = MP;
        this.HP = HP;

        this.LK = LK;
        this.MK = MK;
        this.HK = HK;

        this.buffer = buffer;
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        this.buffer.update();
        int keyPressed = ke.getKeyCode();
        buffer.checkmove(this.character);
        if(keyPressed == KeyEvent.VK_ESCAPE){
            System.exit(0);
        }
        if (keyPressed == up) {
            this.character.activateMove("Jump");
        }
        if (keyPressed == down) {
            this.character.toggleDownPressed();
        }
        if (keyPressed == left) {
            this.character.toggleLeftPressed();
        }
        if (keyPressed == right) {
            this.character.toggleRightPressed();
        }

        if(keyPressed == LP){
            this.character.activateMove("LP");
        }
        if (keyPressed == MP) {
            this.character.activateMove("MP");
        }
        if(keyPressed == HP){
            this.character.activateMove("HP");
        }

        if(keyPressed == LK){
            this.character.activateMove("LK");
        }
        if (keyPressed == MK) {
            this.character.activateMove("MK");
        }
        if(keyPressed == HK){
            this.character.activateMove("HK");
        }

    }

    @Override
    public void keyReleased(KeyEvent ke) {
        this.buffer.update();
        int keyReleased = ke.getKeyCode();
        if(!character.inMove()) {
            if (keyReleased == up) {
                buffer.add(new ButtonPressed("UP"));
            }
            if (keyReleased == down) {
                this.character.unToggleDownPressed();
                buffer.add(new ButtonPressed("DOWN"));
            }
            if (keyReleased == left) {
                this.character.unToggleLeftPressed();
                if (this.character.getFacingLeft()) {
                    buffer.add(new ButtonPressed("LEFT"));
                } else {
                    buffer.add(new ButtonPressed("RIGHT"));
                }
            }
            if (keyReleased == right) {
                this.character.unToggleRightPressed();
                if (this.character.getFacingLeft()) {
                    buffer.add(new ButtonPressed("RIGHT"));
                } else {
                    buffer.add(new ButtonPressed("LEFT"));
                }
            }

            if (keyReleased == LP){
                buffer.add(new ButtonPressed("PUNCH"));
            }
            if (keyReleased == MP){
                buffer.add(new ButtonPressed("PUNCH"));
            }
            if (keyReleased == HP){
                buffer.add(new ButtonPressed("PUNCH"));
            }

            if (keyReleased == LK){
                buffer.add(new ButtonPressed("LK"));
            }
            if (keyReleased == MK) {
                buffer.add(new ButtonPressed("MK"));
            }
            if(keyReleased == HK){
                buffer.add(new ButtonPressed("HK"));
            }
        }
        buffer.checkmove(this.character);
    }
}