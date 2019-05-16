package GameFiles.CharacterStates.Animations;

import java.awt.Image;
import java.awt.image.BufferedImage;


public abstract class Animation {
    public abstract void addFrame(BufferedImage image, long duration);
    public abstract void start();
    public abstract void update(long elapsedTime);
    public abstract int getCurrFrameIndex();
    public abstract Image getImage();
    public abstract int getLength();
    public abstract int getOffsetX();
    public abstract int getOffsetY();
}


