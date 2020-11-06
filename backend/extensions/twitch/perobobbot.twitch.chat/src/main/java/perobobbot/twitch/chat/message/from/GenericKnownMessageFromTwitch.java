package perobobbot.twitch.chat.message.from;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import perobobbot.common.irc.IRCParsing;
import perobobbot.twitch.chat.message.IRCCommand;

/**
 * @author perococco
 **/
@Getter
@ToString
public class GenericKnownMessageFromTwitch extends KnownMessageFromTwitch {

    @NonNull
    private final IRCCommand command;

    public GenericKnownMessageFromTwitch(@NonNull IRCParsing ircParsing, @NonNull IRCCommand command) {
        super(ircParsing);
        this.command = command;
    }

    @Override
    public <T> T accept(@NonNull MessageFromTwitchVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
