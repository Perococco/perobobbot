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
public class PongFromTwitch extends MessageFromTwitchBase implements KnownMessageFromTwitch {

    @NonNull
    @Getter
    private final IRCParsing ircParsing;

    @Override
    public @NonNull IRCCommand command() {
        return IRCCommand.PONG;
    }


    public static @NonNull MessageFromTwitch build(@NonNull AnswerBuilderHelper helper) {
        return new PongFromTwitch(helper.ircParsing());
    }
}
