package perobobbot.ndi;

import com.walker.devolay.Devolay;
import com.walker.devolay.DevolayFrameFourCCType;
import com.walker.devolay.DevolaySender;
import com.walker.devolay.DevolayVideoFrame;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Hashtable;

public class Main {

    public static void main(String[] args) {
//        new Scanner(System.in).nextLine();
        Devolay.loadLibraries();

        DevolaySender sender = new DevolaySender("Overlay");

        final int width = 1920;
        final int height = 1080;
        // BGRA has a pixel depth of 4
        final int pixelDepth = 4;

        DevolayVideoFrame videoFrame = new DevolayVideoFrame();
        videoFrame.setResolution(width, height);
        videoFrame.setFourCCType(DevolayFrameFourCCType.BGRA);
        videoFrame.setLineStride(width * pixelDepth);
        videoFrame.setFrameRate(30_000, 1_001);

        BufferedImage image = createImage(width, height);
        // Use two frame buffers because one will typically be in flight (being used by NDI send) while the other is being filled.
        ByteBuffer[] frameBuffers = {
                ByteBuffer.allocateDirect(width * height * pixelDepth),
                ByteBuffer.allocateDirect(width * height * pixelDepth)};

        int frameCounter = 0;
        FPSCounter fpsCounter = new FPSCounter();
        // Run for one minute
        long startTime = System.currentTimeMillis();

        long loopStart;
        while (System.currentTimeMillis() - startTime < 2000 * 60) {
            loopStart = System.nanoTime();
            // Use the buffer that currently isn't in flight
            ByteBuffer buffer = frameBuffers[frameCounter & 1];

            // Fill in the buffer for one frame.
            fillFrame(width, height, frameCounter, buffer, image);
            videoFrame.setData(buffer);

            // Submit the frame asynchronously.
            // This call will return immediately and the API will "own" the buffer until a synchronizing event.
            // A synchronizing event is one of: DevolaySender#sendVideoFrameAsync, DevolaySender#sendVideoFrame, DevolaySender#close
            final long tick = System.nanoTime();
            sender.sendVideoFrameAsync(videoFrame);
            final long tack = System.nanoTime();
            System.out.format("Last : %d %d %.1f %n", (tick-loopStart)/1000, (tack-tick)/1000, 1e9/(tack-loopStart));


            // Give an FPS message every 30 frames submitted
            if (frameCounter % 30 == 29) {
                fpsCounter.display(30);
            }

            frameCounter++;
        }

        // Destroy the references to each. Not necessary, but can free up the memory faster than Java's GC by itself
        videoFrame.close();
        sender.close();
    }

    private static BufferedImage createImage(int width, int height) {
        final ColorModel colorModel = new
                DirectColorModel(
                ColorSpace.getInstance(ColorSpace.CS_sRGB),
                32,
                0x0000ff00,// Red
                0x00ff0000,// Green
                0xff000000,// Blue
                0x000000ff,// Alpha
                false,       // Alpha Premultiplied
                DataBuffer.TYPE_INT
        );
        final WritableRaster raster = colorModel.createCompatibleWritableRaster(width,
                                                                                height);

        return new BufferedImage(colorModel, raster, false, new Hashtable<>());
    }

    private static final Color BKG = new Color(255, 255, 255, 0);
    private static final Color HALF1 = new Color(0, 255, 0, 64);
    private static final Color HALF2 = new Color(255, 0, 0, 64);

    private static class FPSCounter {

        private long current;

        public void start() {
            current = System.nanoTime();
        }

        public void display(int nbFrames) {
            final long last = current;
            current = System.nanoTime();
            long timeSpent = current - last;
            System.out.printf("Average FPS: %5.1f%n", 3e10f / (timeSpent));

        }
    }

    private static void fillFrame(int width, int height, int frameCounter, ByteBuffer data, BufferedImage image) {
        final Graphics2D graphics2D = image.createGraphics();
        try {
            final int radius = width / 8;
            graphics2D.setBackground(BKG);
            graphics2D.clearRect(0, 0, width, height);
            double frameOffset = (Math.sin(frameCounter / 120d) / 4);
            graphics2D.translate(width * 0.5, height * 0.5);

            final int x = (int) (width * frameOffset) - radius;
            final int y = (int) (height * frameOffset) - radius;
//            graphics2D.setComposite(AlphaComposite.DstOver);
            graphics2D.setPaint(HALF1);
            graphics2D.fillOval(x, y, radius * 2, radius * 2);
            graphics2D.setPaint(HALF2);
            graphics2D.fillOval(x, -y, radius, radius);

        } finally {
            graphics2D.dispose();
        }
        final DataBuffer dataBuffer = image.getData().getDataBuffer();
        if (dataBuffer instanceof DataBufferByte) {
            data.position(0);
            data.put(((DataBufferByte) dataBuffer).getData());
            data.flip();
        } else if (dataBuffer instanceof DataBufferInt) {
            final DataBufferInt b = (DataBufferInt) dataBuffer;
            final IntBuffer tgt = data.asIntBuffer();
            tgt.position(0);
            tgt.put(IntBuffer.wrap(b.getData()));
            tgt.flip();
        }
    }
}
