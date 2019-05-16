/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameFiles;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;

public class DebugControl implements KeyListener {
    public DebugControl() {

    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if(ke.getKeyCode() == KeyEvent.VK_SPACE){
            if(GameWorld.debug) {
                GameWorld.debug = false;
            }
            else {
                GameWorld.debug = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {

    }
}