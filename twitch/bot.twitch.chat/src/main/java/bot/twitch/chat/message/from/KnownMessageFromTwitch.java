package bot.twitch.chat.message.from;

import bot.twitch.chat.message.IRCCommand;
import lombok.NonNull;

/**
 * @author perococco
 **/
public interface KnownMessageFromTwitch extends MessageFromTwitch {

    @NonNull
    IRCCommand command();

}
