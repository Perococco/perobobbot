package perobobbot.twitch.chat.message.from;

import lombok.NonNull;
import perobobbot.common.irc.IRCParsing;
import perobobbot.twitch.chat.message.IRCCommand;

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
