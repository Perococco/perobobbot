package perobobbot.twitch.chat.message.to;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.chat.advanced.Request;
import perobobbot.lang.fp.TryResult;
import perobobbot.twitch.chat.TwitchChatState;
import perobobbot.twitch.chat.message.IRCCommand;
import perobobbot.twitch.chat.message.from.MessageFromTwitch;

import java.util.Optional;

/**
 * @author perococco
 **/
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
public abstract class RequestToTwitch<A> extends MessageToTwitch implements Request<A> {

    @NonNull
    private final IRCCommand command;

    @NonNull
    private final Class<A> answerType;

    @NonNull
    public abstract Optional<TryResult<Throwable,A>> isMyAnswer(@NonNull MessageFromTwitch messageFromTwitch, @NonNull TwitchChatState state);

    @Override
    public String commandInPayload() {
        return command.name();
    }
}
