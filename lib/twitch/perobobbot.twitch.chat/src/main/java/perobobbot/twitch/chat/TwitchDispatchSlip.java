package perobobbot.twitch.chat;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.chat.core.DispatchSlip;
import perobobbot.twitch.chat.message.to.CommandToTwitch;
import perococco.perobobbot.twitch.chat.TwitchMessageChannelIO;

import java.time.Instant;

/**
 * @author perococco
 **/
@RequiredArgsConstructor
public class TwitchDispatchSlip implements DispatchSlip {

    @NonNull
    @Getter
    private final TwitchMessageChannelIO messageChannelIO;

    @NonNull
    @Getter
    private final CommandToTwitch sentCommand;

    @NonNull
    @Getter
    private final Instant dispatchTime;

}
