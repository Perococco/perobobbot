package bot.twitch.chat.message.to;

import bot.chat.advanced.Request;
import bot.common.lang.fp.TryResult;
import bot.twitch.chat.TwitchChatState;
import bot.twitch.chat.message.IRCCommand;
import bot.twitch.chat.message.from.MessageFromTwitch;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

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
