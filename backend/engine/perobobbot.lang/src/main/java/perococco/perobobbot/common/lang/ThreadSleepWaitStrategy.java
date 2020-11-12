package perococco.perobobbot.common.lang;

import lombok.NonNull;
import perobobbot.lang.WaitStrategy;

import java.time.Duration;

public class ThreadSleepWaitStrategy implements WaitStrategy {

    public static ThreadSleepWaitStrategy create() {
        return ThreadSleepWaitStrategy.Holder.INSTANCE;
    }


    @Override
    public void waitFor(@NonNull Duration duration) throws InterruptedException {
        if (!duration.isNegative() && !duration.isZero()) {
            Thread.sleep(duration.toMillis(), duration.toNanosPart());
        }
    }

    private static class Holder {
        private static final ThreadSleepWaitStrategy INSTANCE = new ThreadSleepWaitStrategy();
    }

}
