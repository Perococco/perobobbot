package bot.twitch.chat.message.from;

import bot.common.irc.IRCParsing;
import bot.twitch.chat.Badge;
import bot.twitch.chat.Badges;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import perococco.bot.twitch.chat.BadgeParser;

import java.util.Optional;

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
        return "UnknownMessageFromTwitch{"+payload()+"}";
    }
}
