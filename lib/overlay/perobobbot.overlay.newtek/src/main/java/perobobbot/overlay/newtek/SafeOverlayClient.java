package perobobbot.overlay.newtek;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import perobobbot.lang.ThrowableTool;
import perobobbot.overlay.api.OverlayClient;
import perobobbot.overlay.api.OverlayIteration;

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
            LOG.error("Overlay client '{}' failed",getDelegate(), t);
            unsafe = true;
        }
    }

    @Override
    public String toString() {
        return "SafeOverlayClient{" +getDelegate()+"}";
    }
}
