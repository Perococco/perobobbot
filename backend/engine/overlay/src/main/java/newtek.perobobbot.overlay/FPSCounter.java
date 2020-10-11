package newtek.perobobbot.overlay;

import lombok.NonNull;
import org.apache.logging.log4j.Logger;

public interface FPSCounter {

    @NonNull
    static FPSCounter create(@NonNull Logger logger) {
        if (logger.isDebugEnabled()) {
            return new LogFPSCounter(logger);
        } else {
            return NOP;
        }
    }

    void start();

    void display(int nbFrames);

    FPSCounter NOP = new FPSCounter() {
        @Override
        public void start() {}

        @Override
        public void display(int nbFrames) {}
    };
}
