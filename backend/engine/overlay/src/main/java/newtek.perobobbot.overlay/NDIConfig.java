package newtek.perobobbot.overlay;

import com.walker.devolay.DevolayFrameFourCCType;
import com.walker.devolay.DevolayVideoFrame;
import lombok.NonNull;
import lombok.Value;
import perobobbot.overlay.FrameRate;

import java.nio.ByteBuffer;

@Value
public class NDIConfig {

    public static final int PIXEL_DEPTH = 4;

    int width;
    int height;
    @NonNull DevolayFrameFourCCType ccType;
    @NonNull FrameRate frameRate;

    @NonNull
    public DevolayVideoFrame createNDIFrame() {
        final DevolayVideoFrame videoFrame = new DevolayVideoFrame();
        videoFrame.setResolution(width, height);
        videoFrame.setFourCCType(ccType);
        videoFrame.setLineStride(width * PIXEL_DEPTH);
        videoFrame.setFrameRate(frameRate.getNumerator(), frameRate.getDenominator());
        return videoFrame;
    }

    @NonNull
    public NDIImage createImage() {
        return NDIImage.create(width,height,ccType);
    }

    @NonNull
    public ByteBuffer createByteBuffer() {
        return ByteBuffer.allocateDirect(width * height * PIXEL_DEPTH);
    }
}
