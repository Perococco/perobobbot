package bot.twitch.chat.message.from;

import bot.common.irc.IRCParsing;
import bot.twitch.chat.Channel;
import bot.twitch.chat.ChannelSpecific;
import bot.twitch.chat.message.IRCCommand;
import lombok.*;

/**
 * @author perococco
 **/
@Getter
@ToString
public class Join extends KnownMessageFromTwitch implements ChannelSpecific {


    @NonNull
    private final String user;

    @NonNull
    private final Channel channel;

    @Builder
    public Join(@NonNull IRCParsing ircParsing, @NonNull String user, @NonNull Channel channel) {
        super(ircParsing);
        this.user = user;
        this.channel = channel;
    }

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
