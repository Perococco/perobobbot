package newtek.perobobbot.overlay;

import lombok.NonNull;

import java.awt.*;
import java.nio.ByteBuffer;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class NDIData {

    @NonNull
    private final NDIImage image;

    @NonNull
    private final BlockingDeque<ByteBuffer> freeBuffers;

    @NonNull
    private final BlockingDeque<ByteBuffer> pendingBuffers;

    public NDIData(@NonNull NDIConfig config, int bufferingSize) {
        this.image = config.createImage();
        this.freeBuffers = new LinkedBlockingDeque<>(bufferingSize);
        this.pendingBuffers = new LinkedBlockingDeque<>(bufferingSize);
        for (int i = 0; i < bufferingSize; i++) {
            this.freeBuffers.addLast(config.createByteBuffer());
        }
    }

    @NonNull
    public SimpleDrawingContext createDrawingContext() {
        final Graphics2D graphics2D = image.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        return new SimpleDrawingContext(graphics2D, image.getWidth(), image.getHeight());
    }

    @NonNull
    public ByteBuffer takePendingBuffer() throws InterruptedException {
        return pendingBuffers.takeFirst();
    }

    public void releaseBuffer(@NonNull ByteBuffer byteBuffer) {
        this.freeBuffers.push(byteBuffer);
    }

    public void copyImageToFreeBuffer() throws InterruptedException {
        final ByteBuffer buffer = freeBuffers.take();
        image.copyTo(buffer);
        this.pendingBuffers.putLast(buffer);
    }
}
