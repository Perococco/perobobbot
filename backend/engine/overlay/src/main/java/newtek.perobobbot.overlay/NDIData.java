package newtek.perobobbot.overlay;

import lombok.NonNull;
import perobobbot.common.sound.SoundManager;
import perobobbot.overlay.SoundContext;

import java.awt.*;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class NDIData {

    @NonNull
    private final NDIImage image;

    @NonNull
    private final NDISoundContext soundContext;

    @NonNull
    private final BlockingDeque<NDIBuffers> freeBuffers;

    @NonNull
    private final BlockingDeque<NDIBuffers> pendingBuffers;

    private final AudioBufferSizeComputer audioBufferSizeComputer;

    public NDIData(@NonNull NDIConfig config, @NonNull SoundManager soundManager, int bufferingSize) {
        this.image = config.createImage();
        this.soundContext = new NDISoundContext(soundManager);
        this.audioBufferSizeComputer = config.getAudioBufferSizeComputer();
        this.freeBuffers = new LinkedBlockingDeque<>(bufferingSize);
        this.pendingBuffers = new LinkedBlockingDeque<>(bufferingSize);
        for (int i = 0; i < bufferingSize; i++) {
            this.freeBuffers.addLast(config.createBuffer());
        }
    }

    @NonNull
    public SimpleDrawingContext createDrawingContext() {
        final Graphics2D graphics2D = image.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        return new SimpleDrawingContext(graphics2D, image.getWidth(), image.getHeight());
    }

    public SoundContext getSoundContext() {
        return this.soundContext;
    }

    @NonNull
    public NDIBuffers takePendingBuffer() throws InterruptedException {
        return pendingBuffers.takeFirst();
    }

    public void releaseBuffer(@NonNull NDIBuffers buffer) {
        this.freeBuffers.push(buffer);
    }

    public void copyDataToFreeBuffers(long iterationCount) throws InterruptedException {
        final NDIBuffers buffer = freeBuffers.take();
        buffer.nbAudioSamples = audioBufferSizeComputer.getAudioBufferSize(iterationCount);
        soundContext.fillAudioBuffer(buffer.audio,buffer.nbAudioSamples);
        image.copyTo(buffer.video);
        this.pendingBuffers.putLast(buffer);
    }

    public void release() {
        this.soundContext.releaseSounds();
    }
}
