package perobobbot.twitch.chat.message.from;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.common.irc.IRCParsing;

/**
 * @author perococco
 **/
@Getter
public class UnknownMessageFromTwitch extends MessageFromTwitchBase implements MessageFromTwitch {

    public UnknownMessageFromTwitch(@NonNull IRCParsing ircParsing) {
        super(ircParsing);
    }

    @Override
    public <T> T accept(@NonNull MessageFromTwitchVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "UnknownMessageFromTwitch{" + getPayload() + "}";
    }
}
