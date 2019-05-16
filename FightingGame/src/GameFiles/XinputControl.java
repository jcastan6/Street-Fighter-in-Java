/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameFiles;


import GameFiles.Characters.GameCharacter;
import com.github.strikerx3.jxinput.enums.XInputButton;
import com.github.strikerx3.jxinput.listener.XInputDeviceListener;

public class XinputControl implements XInputDeviceListener {

    private GameCharacter character;
    private final XInputButton up;
    private final XInputButton down;
    private final XInputButton right;
    private final XInputButton left;

    private final XInputButton LP;
    private final XInputButton MP;
    private final XInputButton HP;

    private final XInputButton LK;
    private final XInputButton MK;
    private final XInputButton HK;

    private MoveBuffer buffer;

    public XinputControl(GameCharacter character, MoveBuffer buffer, XInputButton up, XInputButton down, XInputButton left, XInputButton right, XInputButton LP, XInputButton MP, XInputButton HP, XInputButton LK, XInputButton MK, XInputButton HK) {
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
    public void connected() {

    }

    @Override
    public void disconnected() {

    }

    @Override
    public void buttonChanged(XInputButton button, final boolean pressed) {
        this.buffer.update();
        this.buffer.update();

        if(pressed == true) {
            if (button == XInputButton.START) {
                System.exit(0);
            }
            if (button == this.up) {
                this.character.activateMove("Jump");
            }
            if (button == this.down) {
                this.character.toggleDownPressed();
            }
            if (button == this.left) {
                this.character.toggleLeftPressed();
            }
            if (button == this.right) {
                this.character.toggleRightPressed();
            }

            if (button == this.LP) {
                this.character.activateMove("LP");
            }
            if (button == this.MP) {
                this.character.activateMove("MP");
            }
            if (button == this.HP) {
                this.character.activateMove("HP");
            }

            if (button == this.LK) {
                this.character.activateMove("LK");
            }
            if (button == this.MK) {
                this.character.activateMove("MK");
            }
            if (button == this.HK) {
                this.character.activateMove("HK");
            }
        }

        if(pressed == false){
            if(!character.inMove()) {
                if (button == this.up) {
                    buffer.add(new ButtonPressed("UP"));
                }
                if (button == this.down) {
                    this.character.unToggleDownPressed();
                    buffer.add(new ButtonPressed("DOWN"));
                }
                if (button == this.left) {
                    this.character.unToggleLeftPressed();
                    if (this.character.getFacingLeft()) {
                        buffer.add(new ButtonPressed("LEFT"));
                    } else {
                        buffer.add(new ButtonPressed("RIGHT"));
                    }
                }
                if (button == this.right) {
                    this.character.unToggleRightPressed();
                    if (this.character.getFacingLeft()) {
                        buffer.add(new ButtonPressed("RIGHT"));
                    } else {
                        buffer.add(new ButtonPressed("LEFT"));
                    }
                }

                if (button == this.LP || button == this.MP || button == this.HP) {
                    buffer.add(new ButtonPressed("PUNCH"));
                }
/*                if (button == XInputButton.Y) {
                    this.character.unTogglePunchPressed();
                    buffer.add(new ButtonPressed("PUNCH"));
                }
                if (button == XInputButton.LEFT_SHOULDER) {
                    this.character.unTogglePunchPressed();
                    buffer.add(new ButtonPressed("PUNCH"));
                }*/

                if (button == this.LK) {
                    buffer.add(new ButtonPressed("LK"));
                }
                if (button == this.MK) {
                    buffer.add(new ButtonPressed("MK"));
                }
                if (button == this.HK) {
                    buffer.add(new ButtonPressed("HK"));
                }
            }

            buffer.checkmove(this.character);
        }
    }


}