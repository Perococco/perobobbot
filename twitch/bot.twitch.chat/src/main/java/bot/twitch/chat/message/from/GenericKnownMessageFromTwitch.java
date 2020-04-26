package bot.twitch.chat.message.from;

import bot.common.irc.IRCParsing;
import bot.twitch.chat.message.IRCCommand;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * @author perococco
 **/
@Getter
@RequiredArgsConstructor
@ToString
public class GenericKnownMessageFromTwitch extends KnownMessageFromTwitch {

    @NonNull
    private final IRCCommand command;

    @NonNull
    private final IRCParsing ircParsing;

    @Override
    public <T> T accept(@NonNull MessageFromTwitchVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
