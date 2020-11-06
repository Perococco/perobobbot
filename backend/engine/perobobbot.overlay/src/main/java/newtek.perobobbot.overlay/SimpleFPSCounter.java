package newtek.perobobbot.overlay;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.common.lang.fp.Consumer1;

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
        long timeSpent = current - last;
        logger.accept(String.format("Average FPS: %5.1f",3e10f / (timeSpent)));
    }
}
