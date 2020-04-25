package bot.twitch.chat.message.from;

import bot.common.irc.IRCParsing;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author perococco
 **/
@RequiredArgsConstructor
@Getter
public class UnknownMessageFromTwitch implements MessageFromTwitch {

    @NonNull
    private final IRCParsing ircParsing;

    @Override
    public void accept(@NonNull MessageFromTwitchVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "UnknownMessageFromTwitch{"+payload()+"}";
    }
}
