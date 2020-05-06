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
public class UserState extends KnownMessageFromTwitch implements ChannelSpecific {


    @NonNull
    private final Channel channel;

    @Builder
    public UserState(@NonNull IRCParsing ircParsing, @NonNull Channel channel) {
        super(ircParsing);
        this.channel = channel;
    }

    @Override
    public @NonNull IRCCommand command() {
        return IRCCommand.USERSTATE;
    }

    @Override
    public <T> T accept(@NonNull MessageFromTwitchVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public static UserState build(@NonNull AnswerBuilderHelper helper) {
        return UserState.builder()
                        .ircParsing(helper.ircParsing())
                        .channel(helper.channelFromParameterAt(0))
                        .build();
    }

}
