package bot.twitch.chat.message.from;

import bot.twitch.chat.message.IRCCommand;
import lombok.NonNull;

/**
 * @author perococco
 **/
public abstract class KnownMessageFromTwitch implements MessageFromTwitch {

    @NonNull
    public abstract IRCCommand command();

    @Override
    public String toString() {
        return getClass().getSimpleName()+"{"+payload()+"}";
    }
}
