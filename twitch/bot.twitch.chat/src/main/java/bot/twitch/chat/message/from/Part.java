package bot.twitch.chat.message.from;

import bot.common.irc.IRCParsing;
import bot.twitch.chat.Channel;
import bot.twitch.chat.ChannelSpecific;
import bot.twitch.chat.message.IRCCommand;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author perococco
 **/
@RequiredArgsConstructor
@Builder
@Getter
public class Part extends KnownMessageFromTwitch implements ChannelSpecific {

    @NonNull
    private final IRCParsing ircParsing;

    @NonNull
    private final String user;

    @NonNull
    private final Channel channel;

    @Override
    public @NonNull IRCCommand command() {
        return IRCCommand.PART;
    }

    @Override
    public <T> T accept(@NonNull MessageFromTwitchVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public static @NonNull Part build(@NonNull AnswerBuilderHelper helper) {
        return Part.builder()
                   .ircParsing(helper.ircParsing())
                   .channel(helper.channelFromParameterAt(0))
                   .user(helper.userFromPrefix())
                   .build();
    }
}
