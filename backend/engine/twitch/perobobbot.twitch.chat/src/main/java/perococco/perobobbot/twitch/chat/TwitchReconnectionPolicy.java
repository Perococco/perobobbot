package perococco.perobobbot.twitch.chat;

import lombok.NonNull;
import perobobbot.chat.core.ReconnectionPolicy;

import java.time.Duration;

public class TwitchReconnectionPolicy implements ReconnectionPolicy {

    @Override
    public boolean shouldReconnect(int nbAttemptsSoFar) {
        return nbAttemptsSoFar<32;
    }

    @Override
    public @NonNull Duration delayBeforeNextAttempt(int nexAttemptIndex) {
        if (nexAttemptIndex <= 1) {
            return Duration.ZERO;
        }
        return Duration.ofSeconds(1<<(Math.min(30,nexAttemptIndex-2)));
    }
}
