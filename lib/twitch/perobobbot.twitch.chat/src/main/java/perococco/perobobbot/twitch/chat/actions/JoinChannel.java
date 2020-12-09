package perococco.perobobbot.twitch.chat.actions;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.twitch.chat.Channel;
import perobobbot.twitch.chat.message.from.UserState;
import perobobbot.twitch.chat.message.to.Join;
import perococco.perobobbot.twitch.chat.TwitchIO;
import perococco.perobobbot.twitch.chat.state.mutator.OperatorUsingIO;

import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
public class JoinChannel implements OperatorUsingIO<CompletionStage<UserState>> {

    @NonNull
    private final String nick;

    @NonNull
    private final Channel channel;

    @Override
    public @NonNull CompletionStage<UserState> withIO(@NonNull TwitchIO io) {
        return io.sendToChat(new Join(nick,channel))
                 .thenApply(TwitchIO.ReceiptSlip::getAnswer);
    }
}
