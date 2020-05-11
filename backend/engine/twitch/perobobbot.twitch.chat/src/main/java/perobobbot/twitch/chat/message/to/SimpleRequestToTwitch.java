package perobobbot.twitch.chat.message.to;

import perobobbot.common.lang.CastTool;
import perobobbot.common.lang.fp.TryResult;
import perobobbot.twitch.chat.TwitchChatState;
import perobobbot.twitch.chat.message.IRCCommand;
import perobobbot.twitch.chat.message.from.MessageFromTwitch;
import lombok.NonNull;

import java.util.Optional;

/**
 * @author perococco
 **/
public abstract class SimpleRequestToTwitch<A> extends RequestToTwitch<A> {

    private final Class<A> expectedAnswerType;

    SimpleRequestToTwitch(@NonNull IRCCommand command, @NonNull Class<A> expectedAnswerType) {
        super(command, expectedAnswerType);
        this.expectedAnswerType = expectedAnswerType;
    }


    @Override
    public final @NonNull Optional<TryResult<Throwable, A>> isMyAnswer(@NonNull MessageFromTwitch messageFromTwitch, @NonNull TwitchChatState state) {
        return CastTool.cast(expectedAnswerType, messageFromTwitch)
                       .flatMap(a -> doIsMyAnswer(a, state))
                       .map(TryResult::success);
    }

    @NonNull
    protected abstract Optional<A> doIsMyAnswer(@NonNull A twitchAnswer, @NonNull TwitchChatState state);
}
