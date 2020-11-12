package perobobbot.twitch.chat.message.from;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import perobobbot.irc.IRCParsing;
import perobobbot.twitch.chat.Channel;
import perobobbot.twitch.chat.ChannelSpecific;
import perobobbot.twitch.chat.message.IRCCommand;

/**
 * @author perococco
 **/
@Getter
@ToString
public class UserNotice extends KnownMessageFromTwitch implements ChannelSpecific {

    @NonNull
    private final Channel channel;

    @NonNull
    private final String message;

    public UserNotice(@NonNull IRCParsing ircParsing, @NonNull Channel channel, @NonNull String message) {
        super(ircParsing);
        this.channel = channel;
        this.message = message;
    }

    @Override
    public @NonNull IRCCommand getCommand() {
        return IRCCommand.USERNOTICE;
    }

    @Override
    public <T> T accept(@NonNull MessageFromTwitchVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @NonNull
    public static UserNotice build(@NonNull AnswerBuilderHelper helper) {
        return new UserNotice(
                helper.getIrcParsing(),
                helper.channelFromParameterAt(0),
                helper.lastParameter()
        );
    }
}
