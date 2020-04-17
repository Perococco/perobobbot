package bot.twitch.chat;

import bot.chat.advanced.DispatchSlip;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Delegate;

/**
 * @author perococco
 **/
@Value
public class TwitchDispatchSlip implements TwitchChat, DispatchSlip {

    @NonNull
    @Delegate
    private final TwitchChat twitchChat;

    @NonNull
    @Delegate
    private final DispatchSlip value;
}
