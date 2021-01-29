package perobobbot.pause;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.overlay.api.Overlay;
import perobobbot.overlay.api.OverlayClient;
import perobobbot.overlay.api.OverlayIteration;
import perobobbot.rendering.HAlignment;
import perobobbot.rendering.Renderer;
import perobobbot.rendering.ScreenSize;
import perobobbot.rendering.VAlignment;

import java.awt.*;
import java.time.Duration;

@RequiredArgsConstructor
public class PauseOverlayClient implements OverlayClient {

    private final Duration duration;

    private long overlayStartInstant;

    private ScreenSize size;

    @Override
    public void initialize(@NonNull Overlay overlay) {
        this.overlayStartInstant = System.nanoTime();
        this.size = overlay.getOverlaySize();
    }

    @Override
    public void render(@NonNull OverlayIteration iteration) {
        final var renderer = iteration.getRenderer();
        renderer.withPrivateContext(this::displayMessage);
    }

    private void displayMessage(@NonNull Renderer renderer) {
        final String timerMessage = getTimerMessage();
        renderer.blockBuilder()
                .setBackgroundMargin(60)
                .setBackgroundColor(new Color(0,0,0,128))
                .setFontSize(120f)
                .addString("C'est la pause",HAlignment.MIDDLE)
                .setFontSize(80f)
                .addString(timerMessage,HAlignment.MIDDLE)
                .build()
                .draw(
                size.getWidth() * 0.5,
                size.getHeight() * 0.5,
                HAlignment.MIDDLE,
                VAlignment.MIDDLE);
    }

    private String getTimerMessage() {
        final long remaining = Math.max(0,duration.toNanos() - (System.nanoTime() - overlayStartInstant))/(1_000_000_000);
        final String msg;
        if (remaining<=0) {
            msg ="J'arrive";
        } else if (remaining<60) {
            msg = String.format("%d s",remaining);
        } else {
            msg = String.format("%d min %02d s",remaining/60,remaining%60);
        }
        return msg;
    }

}
