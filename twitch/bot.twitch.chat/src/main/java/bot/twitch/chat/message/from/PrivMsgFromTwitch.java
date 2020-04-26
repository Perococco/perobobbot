package bot.twitch.chat.message.from;

import bot.common.irc.IRCParsing;
import bot.twitch.chat.Channel;
import bot.twitch.chat.ChannelSpecific;
import bot.twitch.chat.message.IRCCommand;
import lombok.*;

/**
 * @author perococco
 **/
@RequiredArgsConstructor
@Getter
@Builder
@ToString(exclude = "ircParsing")
public class PrivMsgFromTwitch extends KnownMessageFromTwitch implements ChannelSpecific {

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

    @Override
    public <T> T accept(@NonNull MessageFromTwitchVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @NonNull
    public static PrivMsgFromTwitch build(@NonNull AnswerBuilderHelper helper) {
        return PrivMsgFromTwitch.builder()
                                .ircParsing(helper.ircParsing())
                                .user(helper.userFromPrefix())
                                .channel(helper.channelFromParameterAt(0))
                                .message(helper.lastParameter())
                                .build();
    }
}
