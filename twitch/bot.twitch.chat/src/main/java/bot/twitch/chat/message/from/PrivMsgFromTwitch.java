package bot.twitch.chat.message.from;

import bot.common.irc.IRCParsing;
import bot.twitch.chat.Channel;
import bot.twitch.chat.message.IRCCommand;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author perococco
 **/
@RequiredArgsConstructor
@Getter
@Builder
public class PrivMsgFromTwitch implements KnownMessageFromTwitch {

    @NonNull
    private final IRCParsing ircParsing;

    @NonNull
    private final String user;

    @NonNull
    private final Channel channel;

    @NonNull
    private final String message;

    @Override
    public @NonNull IRCCommand command() {
        return IRCCommand.PRIVMSG;
    }

    @NonNull
    public static PrivMsgFromTwitch build(@NonNull AnswerBuilderHelper helper) {
        return PrivMsgFromTwitch.builder()
                                .ircParsing(helper.ircParsing())
                                .user(helper.userFromPrefix())
                                .channel(helper.channelFormParameterAt(0))
                                .message(helper.lastParameter())
                                .build();
    }
}
