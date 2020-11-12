package perobobbot.twitch.chat.message.from;

import lombok.NonNull;
import perobobbot.irc.IRCParsing;
import perobobbot.twitch.chat.message.IRCCommand;

/**
 * @author perococco
 **/
public class PingFromTwitch extends KnownMessageFromTwitch {


    public PingFromTwitch(@NonNull IRCParsing ircParsing) {
        super(ircParsing);
    }

    @Override
    public @NonNull IRCCommand getCommand() {
        return IRCCommand.PING;
    }

    @Override
    public <T> T accept(@NonNull MessageFromTwitchVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public static  @NonNull MessageFromTwitch build(@NonNull AnswerBuilderHelper helper) {
        return new PingFromTwitch(helper.getIrcParsing());
    }
}
