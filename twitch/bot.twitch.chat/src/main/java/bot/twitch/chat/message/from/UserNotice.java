package bot.twitch.chat.message.from;

import bot.common.irc.IRCParsing;
import bot.twitch.chat.Channel;
import bot.twitch.chat.message.IRCCommand;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * @author perococco
 **/
@RequiredArgsConstructor
@Getter
@ToString(exclude = "ircParsing")
public class UserNotice implements KnownMessageFromTwitch {

    @NonNull
    public IRCParsing ircParsing;

    @NonNull
    private final Channel channel;

    @NonNull
    private final String message;

    @Override
    public @NonNull IRCCommand command() {
        return IRCCommand.USERNOTICE;
    }

    @NonNull
    public static UserNotice build(@NonNull AnswerBuilderHelper helper) {
        return new UserNotice(
                helper.ircParsing(),
                helper.channelFormParameterAt(0),
                helper.lastParameter()
        );
    }
}
