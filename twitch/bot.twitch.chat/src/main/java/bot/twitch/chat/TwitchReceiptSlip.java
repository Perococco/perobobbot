package bot.twitch.chat;

import bot.chat.advanced.ReceiptSlip;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.Delegate;

import javax.swing.*;
import java.nio.file.attribute.UserPrincipalLookupService;

/**
 * @author perococco
 **/
@RequiredArgsConstructor
public class TwitchReceiptSlip<A> implements TwitchChat {

    @NonNull
    @Delegate
    private final TwitchChat twitchChat;

    @NonNull
    @Getter
    private final ReceiptSlip<A> slip;

    @NonNull
    public A slipAnswer() {
        return slip.answer();
    }
}
