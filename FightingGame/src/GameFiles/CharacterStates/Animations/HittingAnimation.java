package GameFiles.CharacterStates.Animations;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class HittingAnimation extends Animation {
    private ArrayList<AnimFrame> frames;
    private int currFrameIndex;
    private long animTime;
    private long totalDuration;
    private boolean end;

    private final int DEFDURATION = 50;
    private int offsetX;
    private int offsetY;

    public HittingAnimation(String character, String move, int offsetX, int offsetY, int duration) {
        frames = new ArrayList();
        totalDuration = 0;

        File dir = new File("FightingGame/Resources/Sprites/" + character + "/" + move + "/");

        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File pic : directoryListing) {
                BufferedImage fixed = null;

                try {
                    fixed = ImageIO.read(pic);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(fixed != null) {
                    BufferedImage after = new BufferedImage(fixed.getWidth() * 2, fixed.getHeight() * 2, BufferedImage.TYPE_INT_ARGB);
                    AffineTransform at = new AffineTransform();
                    at.scale(2.0, 2.0);
                    AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC);
                    after = scaleOp.filter(fixed, after);
                    this.addFrame(after, duration);
                }
            }
        }

        this.offsetX = offsetX;
        this.offsetY = offsetY;
        start();
    }

    public void addFrame(BufferedImage image, long duration) {
        totalDuration += duration;
        frames.add(new AnimFrame(image, totalDuration));
    }

    public void start() {
        end = false;
        animTime = 0;
        currFrameIndex = 0;
    }

    public void update(long elapsedTime) {
        if (frames.size() > 1) {
            animTime += elapsedTime;

            if (animTime >= totalDuration) {
                animTime = animTime % totalDuration;
                currFrameIndex = 0;
                end = true;
            }

            while (animTime > getFrame(currFrameIndex).endTime) {
                currFrameIndex++;
            }
        }
    }

    public int getCurrFrameIndex() {
        return currFrameIndex;
    }

    public BufferedImage getImage() {
        if (frames.size() == 0) {
            return null;
        } else {
            return getFrame(currFrameIndex).image;
        }
    }

    private AnimFrame getFrame(int i) {
        return frames.get(i);
    }

    private class AnimFrame {
        BufferedImage image;
        long endTime;

        public AnimFrame(BufferedImage image, long endTime) {
            this.image = image;
            this.endTime = endTime;
        }
    }

    public int getLength() {
        return frames.size();
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

}

