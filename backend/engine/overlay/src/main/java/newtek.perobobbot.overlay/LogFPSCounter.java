package newtek.perobobbot.overlay;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;

@RequiredArgsConstructor
public class LogFPSCounter implements FPSCounter {

    private long current;

    @NonNull
    private final Logger logger;

    public void start() {
        current = System.nanoTime();
    }

    public void display(int nbFrames) {
        final long last = current;
        current = System.nanoTime();
        long timeSpent = current - last;
        logger.debug(String.format("Average FPS: %5.1f%n",3e10f / (timeSpent)));
    }
}
