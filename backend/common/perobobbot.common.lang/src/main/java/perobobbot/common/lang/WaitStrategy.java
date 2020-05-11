package perobobbot.common.lang;

import lombok.NonNull;
import perococco.perobobbot.common.lang.SpinWaitStrategy;
import perococco.perobobbot.common.lang.ThreadSleepWaitStrategy;

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
