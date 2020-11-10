package perobobbot.overlay.api._private;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.common.lang.fp.Consumer1;
import perobobbot.overlay.api.FPSCounter;

@RequiredArgsConstructor
public class SimpleFPSCounter implements FPSCounter {

    private long current;

    @NonNull
    private final Consumer1<String> logger;

    public void start() {
        current = System.nanoTime();
    }

    public void display(int nbFrames) {
        final long last = current;
        current = System.nanoTime();
        double timeSpent = (current - last)*1e-9;
        logger.accept(String.format("Average FPS: %5.1f", nbFrames / timeSpent));
    }
}
