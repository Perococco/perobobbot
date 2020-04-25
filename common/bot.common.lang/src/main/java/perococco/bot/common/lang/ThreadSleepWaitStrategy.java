package perococco.bot.common.lang;

import bot.common.lang.WaitStrategy;
import lombok.NonNull;

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
