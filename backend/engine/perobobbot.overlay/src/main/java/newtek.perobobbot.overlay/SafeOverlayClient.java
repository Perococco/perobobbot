package newtek.perobobbot.overlay;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import perobobbot.common.lang.ThrowableTool;
import perobobbot.overlay.OverlayClient;
import perobobbot.overlay.OverlayIteration;

@Log4j2
public class SafeOverlayClient extends ProxyOverlayClient {

    private boolean unsafe = false;

    public SafeOverlayClient(@NonNull OverlayClient delegate) {
        super(delegate);
    }

    @Override
    public void render(@NonNull OverlayIteration iteration) {
        try {
            if (unsafe) {
                return;
            }
            super.render(iteration);
        } catch (Throwable t) {
            ThrowableTool.interruptThreadIfCausedByInterruption(t);
            LOG.warn("Overlay client '{}' failed",getDelegate(), t);
            unsafe = true;
        }
    }
}
