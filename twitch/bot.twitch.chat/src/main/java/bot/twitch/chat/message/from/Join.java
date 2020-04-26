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
@Builder
@Getter
@ToString(exclude = "ircParsing")
public class Join extends KnownMessageFromTwitch implements ChannelSpecific {

    @NonNull
    private final IRCParsing ircParsing;

    @NonNull
    private final String user;

    @NonNull
    private final Channel channel;

    @Override
    public @NonNull IRCCommand command() {
        return IRCCommand.JOIN;
    }

    @Override
    public <T> T accept(@NonNull MessageFromTwitchVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public static @NonNull Join build(@NonNull AnswerBuilderHelper helper) {
        return Join.builder()
                   .ircParsing(helper.ircParsing())
                   .user(helper.userFromPrefix())
                   .channel(helper.channelFromParameterAt(0))
                   .build();
    }
}
