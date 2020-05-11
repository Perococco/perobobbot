package perococco.perobobbot.common.lang;

import perobobbot.common.lang.WaitStrategy;
import lombok.NonNull;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CountdownLatchStrategy implements WaitStrategy {

    @Override
    public void waitFor(@NonNull Duration duration) throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        latch.await(duration.toMillis(), TimeUnit.MILLISECONDS);
    }
}
