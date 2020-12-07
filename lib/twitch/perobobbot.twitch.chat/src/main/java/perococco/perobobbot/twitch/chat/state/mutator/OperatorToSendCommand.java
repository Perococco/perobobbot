package perococco.perobobbot.twitch.chat.state.mutator;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.twitch.chat.message.to.CommandToTwitch;
import perococco.perobobbot.twitch.chat.TwitchIO;

import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class OperatorToSendCommand implements OperatorUsingIO<CompletionStage<TwitchIO.DispatchSlip>> {

    public static @NonNull OperatorToSendCommand create(@NonNull CommandToTwitch  command) {
        return new OperatorToSendCommand(command);
    }

    private final @NonNull CommandToTwitch command;

    @Override
    public @NonNull CompletionStage<TwitchIO.DispatchSlip> withIO(@NonNull TwitchIO io) {
        return io.sendToChat(command);
    }
}
