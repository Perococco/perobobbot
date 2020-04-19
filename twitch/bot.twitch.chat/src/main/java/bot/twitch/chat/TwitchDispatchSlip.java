package bot.twitch.chat;

import bot.chat.advanced.DispatchSlip;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.Delegate;

/**
 * @author perococco
 **/
@RequiredArgsConstructor
public class TwitchDispatchSlip implements TwitchChat, DispatchSlip {

    @NonNull
    @Delegate
    private final TwitchChat twitchChat;

    @NonNull
    @Delegate
    private final DispatchSlip value;
}
