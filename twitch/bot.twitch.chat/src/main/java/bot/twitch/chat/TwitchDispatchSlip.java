package bot.twitch.chat;

import bot.chat.advanced.DispatchSlip;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

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
    private final DispatchSlip slip;
}
