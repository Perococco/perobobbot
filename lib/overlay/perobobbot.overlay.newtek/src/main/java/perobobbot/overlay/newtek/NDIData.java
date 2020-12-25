package perobobbot.overlay.newtek;

import lombok.NonNull;
import perobobbot.overlay.api.SoundContext;
import perobobbot.rendering.Renderer;
import perobobbot.rendering.Size;
import perobobbot.sound.SoundManager;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.stream.Stream;

public class NDIData {

    private final @NonNull NDIImage image;

    private final @NonNull Size overlaySize;

    private final @NonNull NDISoundContext soundContext;

    private final @NonNull BlockingDeque<NDIBuffers> freeBuffers;

    private final @NonNull BlockingDeque<NDIBuffers> pendingBuffers;

    private final @NonNull AudioBufferSizeComputer audioBufferSizeComputer;

    public NDIData(@NonNull NDIConfig config, @NonNull SoundManager soundManager, int bufferingSize) {
        this.image = config.createImage();
        this.overlaySize = config.getOverlaySize();
        this.soundContext = new NDISoundContext(soundManager);
        this.audioBufferSizeComputer = config.getAudioBufferSizeComputer();
        this.freeBuffers = new LinkedBlockingDeque<>(bufferingSize);
        this.pendingBuffers = new LinkedBlockingDeque<>(bufferingSize);
        for (int i = 0; i < bufferingSize; i++) {
            this.freeBuffers.addLast(config.createBuffer());
        }
    }

    @NonNull
    public Renderer createOverlayRenderer() {
        return Renderer.withGraphics2D(image.createGraphics(), overlaySize);
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
        try {
            buffer.nbAudioSamples = audioBufferSizeComputer.getAudioBufferSize(iterationCount);
            soundContext.fillAudioBuffer(buffer.audio, buffer.nbAudioSamples);
            image.copyTo(buffer.video);
            this.pendingBuffers.putLast(buffer);
        } catch (Throwable t) {
            this.freeBuffers.offerLast(buffer);
        }
    }

    public void release() {
        this.soundContext.releaseSounds();
    }

    public @NonNull Optional<NDIBuffers> takeAnyBuffers() {
        return Stream.of(
                this.freeBuffers,
                this.pendingBuffers
        ).map(BlockingDeque::peek)
                     .filter(Objects::nonNull)
                     .findFirst();
    }
}
