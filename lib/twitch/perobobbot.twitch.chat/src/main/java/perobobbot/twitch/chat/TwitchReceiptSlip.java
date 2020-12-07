package perobobbot.twitch.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import perobobbot.twitch.chat.message.to.RequestToTwitch;
import perococco.perobobbot.twitch.chat.TwitchChannelIO;

import java.time.Duration;
import java.time.Instant;

/**
 * @author perococco
 **/
@RequiredArgsConstructor
@Builder
public class TwitchReceiptSlip<A> {

    @NonNull
    @Getter
    private final TwitchChannelIO twitchChatIO;

    @NonNull
    @Getter
    private final Instant dispatchingTime;

    @NonNull
    @Getter
    private final Instant receptionTime;

    @NonNull
    @Getter
    private final RequestToTwitch<A> sentRequest;

    @NonNull
    @Getter
    private final A answer;

    @NonNull
    public Duration dialogDuration() {
        return Duration.between(dispatchingTime, receptionTime);
    }
}
