package bot.twitch.chat;

import bot.chat.advanced.ReceiptSlip;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.Delegate;

/**
 * @author perococco
 **/
@RequiredArgsConstructor
public class TwitchReceiptSlip<A> implements TwitchChat, ReceiptSlip<A> {

    @NonNull
    @Delegate
    private final TwitchChat twitchChat;

    @NonNull
    @Delegate
    private final ReceiptSlip<A> value;
}
