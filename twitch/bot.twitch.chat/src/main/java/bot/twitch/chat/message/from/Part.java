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
@Getter
public class Part extends KnownMessageFromTwitch implements ChannelSpecific {

    @NonNull
    private final String user;

    @NonNull
    private final Channel channel;

    @Builder
    public Part(@NonNull IRCParsing ircParsing, @NonNull String user, @NonNull Channel channel) {
        super(ircParsing);
        this.user = user;
        this.channel = channel;
    }

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
