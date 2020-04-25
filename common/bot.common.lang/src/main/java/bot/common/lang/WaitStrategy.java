package bot.common.lang;

import lombok.NonNull;
import perococco.bot.common.lang.SpinWaitStrategy;
import perococco.bot.common.lang.ThreadSleepWaitStrategy;

import java.time.Duration;

public interface WaitStrategy {

    void waitFor(@NonNull Duration duration) throws InterruptedException;


    static WaitStrategy create() {
        return threadSleep();
    }

    @NonNull
    static WaitStrategy spinWait() {
        return SpinWaitStrategy.create();
    }

    @NonNull
    static WaitStrategy threadSleep() {
        return ThreadSleepWaitStrategy.create();
    }
}
