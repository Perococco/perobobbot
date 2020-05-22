package perobobbot.twitch.chat.message.from;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import perobobbot.common.irc.IRCParsing;
import perobobbot.twitch.chat.Channel;
import perobobbot.twitch.chat.ChannelSpecific;
import perobobbot.twitch.chat.message.IRCCommand;

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
    public @NonNull IRCCommand getCommand() {
        return IRCCommand.JOIN;
    }

    @Override
    public <T> T accept(@NonNull MessageFromTwitchVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public static @NonNull Join build(@NonNull AnswerBuilderHelper helper) {
        return Join.builder()
                   .ircParsing(helper.getIrcParsing())
                   .user(helper.userFromPrefix())
                   .channel(helper.channelFromParameterAt(0))
                   .build();
    }
}
