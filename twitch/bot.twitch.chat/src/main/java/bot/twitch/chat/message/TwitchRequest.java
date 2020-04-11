package bot.twitch.chat.message;

import bot.chat.advanced.Request;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author perococco
 **/
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public abstract class TwitchRequest<A> extends TwitchMessage implements Request<A> {

    @NonNull
    @Getter
    private final IRCCommand command;

    @NonNull
    @Getter
    private final Class<A> answerType;

}
