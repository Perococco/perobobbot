package perobobbot.twitch.chat.message.from;

import perobobbot.common.irc.IRCParsing;
import perobobbot.twitch.chat.Channel;
import perobobbot.twitch.chat.ChannelSpecific;
import perobobbot.twitch.chat.message.IRCCommand;
import lombok.*;

/**
 * @author perococco
 **/
@Getter
@ToString
public class UserState extends KnownMessageFromTwitch implements ChannelSpecific {


    @NonNull
    private final Channel channel;

    @Builder
    public UserState(@NonNull IRCParsing ircParsing, @NonNull Channel channel) {
        super(ircParsing);
        this.channel = channel;
    }

    @Override
    public @NonNull IRCCommand getCommand() {
        return IRCCommand.USERSTATE;
    }

    @Override
    public <T> T accept(@NonNull MessageFromTwitchVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public static UserState build(@NonNull AnswerBuilderHelper helper) {
        return UserState.builder()
                        .ircParsing(helper.getIrcParsing())
                        .channel(helper.channelFromParameterAt(0))
                        .build();
    }

}
