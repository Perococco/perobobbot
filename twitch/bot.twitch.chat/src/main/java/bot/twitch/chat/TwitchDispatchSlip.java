package bot.twitch.chat;

import bot.chat.advanced.DispatchSlip;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.Delegate;

/**
 * @author perococco
 **/
@RequiredArgsConstructor
public class TwitchDispatchSlip implements TwitchChat {

    @NonNull
    @Delegate
    private final TwitchChat twitchChat;

    @NonNull
    @Getter
    private final DispatchSlip slip;
}
