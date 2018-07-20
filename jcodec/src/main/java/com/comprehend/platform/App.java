package com.comprehend.platform;

import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class App 
{
    public static void main(String[] args ) throws IOException, JCodecException {

        double[] steps = {1, 1, 0.5, 0.33, 0.25, 0.2};
        File file = new File(args[0]);
        FrameGrab grab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(file));
        double nSeconds = grab.getVideoTrack().getMeta().getTotalDuration();

        for (double step: steps) {
            grab.seekToFramePrecise(0);

            long tick = System.currentTimeMillis();
            for (double second = 0; second < nSeconds; second += step) {
                grab.seekToSecondSloppy(second);
                Picture picture = grab.getNativeFrame();

                // store frames
                // BufferedImage bufferedImage = AWTUtil.toBufferedImage(picture);
                // ImageIO.write(bufferedImage, "png", new File("frame" + frame + ".png"));
            }

            long tock = System.currentTimeMillis();
            System.out.println(step + ", " + (nSeconds * 1000 / (tock - tick)));
        }

    }
}
