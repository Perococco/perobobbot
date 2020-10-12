package newtek.perobobbot.overlay;

import lombok.NonNull;
import org.apache.logging.log4j.Logger;

public interface FPSCounter {

    static FPSCounter toLogger(@NonNull Logger logger) {
        if (logger.isDebugEnabled()) {
            return new SimpleFPSCounter(logger::debug);
        }
        return NOP;
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
