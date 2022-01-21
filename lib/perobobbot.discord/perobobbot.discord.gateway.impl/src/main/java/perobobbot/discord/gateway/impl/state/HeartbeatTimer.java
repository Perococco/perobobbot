package perobobbot.discord.gateway.impl.state;

import lombok.NonNull;
import perobobbot.lang.NanoClock;

public class HeartbeatTimer {

    private final long heartbeatInterval;
    private final @NonNull NanoClock clock;

    private long nextHeartbeatTime;

    public HeartbeatTimer(int heartbeatIntervalInMs) {
        this(heartbeatIntervalInMs, Math.random(), System::nanoTime);
    }

    public HeartbeatTimer(int heartbeatIntervalInMs, double initialIntervalFraction, @NonNull NanoClock clock) {
        this.heartbeatInterval = heartbeatIntervalInMs * 1_000_000L;
        this.clock = clock;
        this.nextHeartbeatTime = clock.nanoTime() + (long) (heartbeatInterval * initialIntervalFraction);
    }

    public boolean timeToSendHeartbeat() {
        final var needToBeSent = (clock.nanoTime()- nextHeartbeatTime) >= 0;
        if (needToBeSent) {
            nextHeartbeatTime += heartbeatInterval;
        }
        return needToBeSent;
    }

    public long delayBeforeNextHeartbeat() {
        return nextHeartbeatTime - clock.nanoTime();
    }


}
