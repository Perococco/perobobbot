package bot.twitch.chat.message.from;

import bot.common.irc.IRCParsing;
import bot.twitch.chat.message.IRCCommand;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author perococco
 **/
@RequiredArgsConstructor
public class PongFromTwitch extends KnownMessageFromTwitch {

    @NonNull
    @Getter
    private final IRCParsing ircParsing;

    @Override
    public @NonNull IRCCommand command() {
        return IRCCommand.PONG;
    }

    @Override
    public <T> T accept(@NonNull MessageFromTwitchVisitor<T> visitor) {
        return visitor.visit(this);
    }


    public static @NonNull MessageFromTwitch build(@NonNull AnswerBuilderHelper helper) {
        return new PongFromTwitch(helper.ircParsing());
    }
}
