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
public class PingFromTwitch implements KnownMessageFromTwitch {

    @NonNull
    @Getter
    private final IRCParsing ircParsing;

    @Override
    public @NonNull IRCCommand command() {
        return IRCCommand.PING;
    }

    public static  @NonNull MessageFromTwitch build(@NonNull AnswerBuilderHelper helper) {
        return new PingFromTwitch(helper.ircParsing());
    }
}
