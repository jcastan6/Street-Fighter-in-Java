package GameFiles;

public class ButtonPressed {
    private int framePressed;
    private String key;

    public ButtonPressed(String key){
        this.key = key;
    }

    public void setFramePressed(int framePressed){
        this.framePressed = framePressed;
    }

    public int getFramePressed(){return framePressed;}
    public String getMove(){return  key;}

}
