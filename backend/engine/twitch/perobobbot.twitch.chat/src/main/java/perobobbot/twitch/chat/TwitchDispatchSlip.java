package perobobbot.twitch.chat;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import perobobbot.twitch.chat.message.to.CommandToTwitch;

import java.time.Instant;

/**
 * @author perococco
 **/
@RequiredArgsConstructor
public class TwitchDispatchSlip implements TwitchChatIO {

    @NonNull
    @Delegate
    private final TwitchChatIO twitchChatIO;

    @NonNull
    @Getter
    private final CommandToTwitch sentCommand;

    @NonNull
    @Getter
    private final Instant dispatchingTime;
}
