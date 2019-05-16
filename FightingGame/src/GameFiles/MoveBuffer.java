package GameFiles;

import GameFiles.Characters.Dudley;
import GameFiles.Characters.GameCharacter;
import GameFiles.Characters.Ken;

import java.awt.*;
import java.util.ArrayList;

public class MoveBuffer {
    private ArrayList<ButtonPressed> buffer;
    private int currTime;

    public MoveBuffer(){
        buffer = new ArrayList<>();
    }

    public void add(ButtonPressed button){
        button.setFramePressed(currTime);
        buffer.add(button);
    }

    public void update(){
        long elapsedTime = System.currentTimeMillis() - currTime;
        currTime += elapsedTime;

        for(int i = 0;  i < buffer.size(); i++){
            if(buffer.get(i) != null) {
                if (currTime - buffer.get(i).getFramePressed() >= 380) {
                    buffer.remove(i);
                }
            }
        }
    }

    public void checkmove(GameCharacter character){
        if(character instanceof Ken){
            checkmove((Ken) character);
        }
        else if(character instanceof Dudley){
            checkmove((Dudley) character);
        }

        if(buffer.size() >=2){
            if(buffer.get(buffer.size() - 1).getMove().equals("LEFT") && buffer.get(buffer.size() - 2).getMove().equals("LEFT")){
                character.toggleDash(true);
                buffer.clear();
            }
            else if(buffer.get(buffer.size() - 1).getMove().equals("RIGHT") && buffer.get(buffer.size() - 2).getMove().equals("RIGHT")){
                character.toggleDash(false);
                buffer.clear();
            }
            else if(buffer.get(buffer.size()-1).getMove().equals("PUNCH") && buffer.get(buffer.size()-2).getMove().equals("HK") ||buffer.get(buffer.size()-1).getMove().equals("HK") && buffer.get(buffer.size()-2).getMove().equals("PUNCH")){
                if(buffer.get(buffer.size()-1).getFramePressed() - buffer.get(buffer.size()-2).getFramePressed() <= 50) {
                    character.activateSpecialMove("Taunt");
                    buffer.clear();
                }
            }
        }
    }

    private void checkmove(Ken character) {
        if(buffer.size() >= 5){
            if(buffer.get(buffer.size() - 1).getMove().equals("PUNCH") && buffer.get(buffer.size() - 2).getMove().equals("DOWN") && buffer.get(buffer.size() - 3).getMove().equals("LEFT") && buffer.get(buffer.size() - 4).getMove().equals("DOWN") && buffer.get(buffer.size() - 5).getMove().equals("LEFT")){
                character.activateSpecialMove("Shoryuken");
                buffer.clear();
            }
            else if(buffer.get(buffer.size() - 1).getMove().equals("MK") && buffer.get(buffer.size() - 2).getMove().equals("RIGHT") && buffer.get(buffer.size() - 3).getMove().equals("DOWN") && buffer.get(buffer.size() - 4).getMove().equals("RIGHT") && buffer.get(buffer.size() - 5).getMove().equals("DOWN")){
                character.activateSuperMove("SuperTatsu");
                buffer.clear();
            }
        }


        if(buffer.size() >= 3) {
            if (buffer.get(buffer.size() - 1).getMove().equals("PUNCH") && buffer.get(buffer.size() - 2).getMove().equals("LEFT") && buffer.get(buffer.size() - 3).getMove().equals("DOWN")) {
                character.toggleHadoken();
                buffer.clear();
            }
            else if (buffer.get(buffer.size() - 2).getMove().equals("RIGHT") && buffer.get(buffer.size() - 3).getMove().equals("DOWN")) {
                if(buffer.get(buffer.size() - 1).getMove().equals("LK")){
                    character.activateSpecialMove("LTatsu");
                    buffer.clear();
                }
                else if(buffer.get(buffer.size() - 1).getMove().equals("MK")){
                    character.activateSpecialMove("MTatsu");
                    buffer.clear();
                }
                else if(buffer.get(buffer.size() - 1).getMove().equals("HK")){
                    character.activateSpecialMove("HTatsu");
                    buffer.clear();
                }
            }
        }
    }

    private void checkmove(Dudley character) {

        if(buffer.size() >= 5){
            if(buffer.get(buffer.size() - 1).getMove().equals("PUNCH") && buffer.get(buffer.size() - 2).getMove().equals("DOWN") && buffer.get(buffer.size() - 3).getMove().equals("LEFT") && buffer.get(buffer.size() - 4).getMove().equals("DOWN") && buffer.get(buffer.size() - 5).getMove().equals("LEFT")){
                character.activateSpecialMove("Uppercut");
                buffer.clear();
            }
            else if(buffer.get(buffer.size() - 1).getMove().equals("MK") && buffer.get(buffer.size() - 2).getMove().equals("RIGHT") && buffer.get(buffer.size() - 3).getMove().equals("DOWN") && buffer.get(buffer.size() - 4).getMove().equals("RIGHT") && buffer.get(buffer.size() - 5).getMove().equals("DOWN")){
                character.activateSuperMove("CorkscrewBlow");
                buffer.clear();
            }
        }

        if(buffer.size() >= 4) {
            if(buffer.get(buffer.size() - 2).getMove().equals("LEFT") && buffer.get(buffer.size() - 3).getMove().equals("DOWN") && buffer.get(buffer.size() - 4).getMove().equals("RIGHT")){
                if(buffer.get(buffer.size() - 1).getMove().equals("LK")) {
                    character.activateSpecialMove("DuckingStraight");
                    buffer.clear();
                }
                else if(buffer.get(buffer.size() - 1).getMove().equals("PUNCH")){
                    character.activateSpecialMove("MGB");
                    buffer.clear();
                }
            }

            else if(buffer.get(buffer.size() - 1).getMove().equals("PUNCH") && buffer.get(buffer.size() - 2).getMove().equals("RIGHT") && buffer.get(buffer.size() - 3).getMove().equals("DOWN") && buffer.get(buffer.size() - 4).getMove().equals("LEFT")){
                character.activateSpecialMove("Counter");
                buffer.clear();
            }
        }
    }

    public void draw(Graphics g){
        int offset = 0;

        for(int i = 0; i < buffer.size(); i++){
                ButtonPressed btn = buffer.get(i);
                g.drawRect(800+offset,800,25,25);
                g.setFont(new Font("arial", Font.BOLD, 10));
                g.drawString(btn.getMove(), 800+offset, 500+20);
                offset +=55;
        }
    }

}
