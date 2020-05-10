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
public class PrivMsgFromTwitch extends KnownMessageFromTwitch implements ChannelSpecific {


    @NonNull
    private final String user;

    @NonNull
    private final Channel channel;

    @NonNull
    private final String payload;

    @Builder
    public PrivMsgFromTwitch(@NonNull IRCParsing ircParsing, @NonNull String user, @NonNull Channel channel, @NonNull String payload) {
        super(ircParsing);
        this.user = user;
        this.channel = channel;
        this.payload = payload;
    }

    @Override
    public @NonNull IRCCommand getCommand() {
        return IRCCommand.PRIVMSG;
    }

    @Override
    public <T> T accept(@NonNull MessageFromTwitchVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @NonNull
    public static PrivMsgFromTwitch build(@NonNull AnswerBuilderHelper helper) {
        return PrivMsgFromTwitch.builder()
                                .ircParsing(helper.getIrcParsing())
                                .user(helper.userFromPrefix())
                                .channel(helper.channelFromParameterAt(0))
                                .payload(helper.lastParameter())
                                .build();
    }
}
