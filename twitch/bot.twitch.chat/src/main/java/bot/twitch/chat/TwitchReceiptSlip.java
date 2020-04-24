package bot.twitch.chat;

import bot.chat.advanced.ReceiptSlip;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

/**
 * @author perococco
 **/
@RequiredArgsConstructor
public class TwitchReceiptSlip<A> implements TwitchChatIO {

    @NonNull
    @Delegate
    private final TwitchChatIO twitchChatIO;

    @NonNull
    @Getter
    private final ReceiptSlip<A> slip;

    @NonNull
    public A slipAnswer() {
        return slip.answer();
    }
}
