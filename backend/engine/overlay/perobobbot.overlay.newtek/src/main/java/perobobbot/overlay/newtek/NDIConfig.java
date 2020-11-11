package perobobbot.overlay.newtek;

import com.walker.devolay.DevolayAudioFrame;
import com.walker.devolay.DevolayFrameFourCCType;
import com.walker.devolay.DevolayVideoFrame;
import lombok.Getter;
import lombok.NonNull;
import perobobbot.overlay.api.FrameRate;
import perobobbot.overlay.api.OverlaySize;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;


public class NDIConfig {

    public static final int PIXEL_DEPTH = 4;

    private final @NonNull OverlaySize overlaySize;
    private final @NonNull DevolayFrameFourCCType ccType;
    private final @NonNull FrameRate frameRate;
    private final int audioSampleRate;
    private final int nbChannels;

    @Getter
    private final AudioBufferSizeComputer audioBufferSizeComputer;


    public NDIConfig(@NonNull OverlaySize size, @NonNull DevolayFrameFourCCType ccType, @NonNull FrameRate frameRate, int audioSampleRate, int nbChannels) {
        this.overlaySize = size;
        this.ccType = ccType;
        this.frameRate = frameRate;
        this.audioSampleRate = audioSampleRate;
        this.nbChannels = nbChannels;
        this.audioBufferSizeComputer = AudioBufferSizeComputer.create(frameRate,audioSampleRate);
    }

    @NonNull
    public AudioBufferSizeComputer createAudioBufferSizeComputer() {
        return AudioBufferSizeComputer.create(frameRate,audioSampleRate);
    }

    @NonNull
    public DevolayVideoFrame createNDIFrame() {
        final DevolayVideoFrame videoFrame = new DevolayVideoFrame();
        videoFrame.setResolution(overlaySize.getWidth(), overlaySize.getHeight());
        videoFrame.setFourCCType(ccType);
        videoFrame.setLineStride(overlaySize.getWidth() * PIXEL_DEPTH);
        videoFrame.setFrameRate(frameRate.getNumerator(), frameRate.getDenominator());
        return videoFrame;
    }

    private int maxAudioSampleCounts() {
        return frameRate.getRatio().invert().multiply(audioSampleRate).ceil();
    }

    @NonNull
    public AudioFrame createNDIAudioFrame() {
        final int sampleCount = maxAudioSampleCounts();
        final int channelSize=  sampleCount*Float.BYTES;
        final ByteBuffer data = ByteBuffer.allocateDirect(channelSize*nbChannels)
                                          .order(ByteOrder.LITTLE_ENDIAN);
        final DevolayAudioFrame audioFrame = new DevolayAudioFrame();
        audioFrame.setSampleRate(this.audioSampleRate);
        audioFrame.setChannels(nbChannels);
        audioFrame.setSamples(sampleCount);
        audioFrame.setData(data);
        audioFrame.setChannelStride(channelSize);
        return new AudioFrame(data,audioFrame);
    }

    @NonNull
    public NDIImage createImage() {
        return NDIImage.create(overlaySize, ccType);
    }

    public NDIBuffers createBuffer() {
        return new NDIBuffers(new float[nbChannels][maxAudioSampleCounts()], createVideoBuffer());
    }

    @NonNull
    private ByteBuffer createVideoBuffer() {
        return ByteBuffer.allocateDirect(overlaySize.numberOfPixels() * PIXEL_DEPTH);
    }
}
