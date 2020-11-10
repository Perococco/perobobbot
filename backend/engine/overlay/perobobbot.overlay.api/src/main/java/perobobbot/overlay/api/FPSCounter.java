package perobobbot.overlay.api;

import lombok.NonNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.spi.LoggerContextFactory;
import perobobbot.overlay.api._private.SimpleFPSCounter;

public interface FPSCounter {

    static @NonNull FPSCounter toLogger(@NonNull Logger logger) {
        if (logger.isDebugEnabled()) {
            return new SimpleFPSCounter(logger::debug);
        }
        return NOP;
    }

    static @NonNull FPSCounter toLogger() {
        return toLogger(LogManager.getLogger(FPSCounter.class));
    }

    static FPSCounter toStdOut() {
        return new SimpleFPSCounter(System.out::println);
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
