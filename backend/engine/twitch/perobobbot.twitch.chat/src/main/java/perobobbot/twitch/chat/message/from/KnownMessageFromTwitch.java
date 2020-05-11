package perobobbot.twitch.chat.message.from;

import perobobbot.common.irc.IRCParsing;
import perobobbot.twitch.chat.message.IRCCommand;
import lombok.NonNull;

/**
 * @author perococco
 **/
public abstract class KnownMessageFromTwitch extends MessageFromTwitchBase implements MessageFromTwitch {

    public KnownMessageFromTwitch(@NonNull IRCParsing ircParsing) {
        super(ircParsing);
    }

    @NonNull
    public abstract IRCCommand getCommand();

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + getPayload() + "}";
    }
}
