package perobobbot.twitch.chat.message.from;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import perobobbot.irc.IRCParsing;
import perobobbot.twitch.chat.Channel;
import perobobbot.twitch.chat.ChannelSpecific;
import perobobbot.twitch.chat.message.IRCCommand;

/**
 * @author perococco
 **/
@Getter
public class Mode extends KnownMessageFromTwitch implements ChannelSpecific {

    @NonNull
    private final Channel channel;

    @NonNull
    private final String user;

    private final boolean gainedModeration;

    @Builder
    public Mode(@NonNull IRCParsing ircParsing, @NonNull Channel channel, @NonNull String user, boolean gainedModeration) {
        super(ircParsing);
        this.channel = channel;
        this.user = user;
        this.gainedModeration = gainedModeration;
    }

    @Override
    public @NonNull IRCCommand getCommand() {
        return IRCCommand.MODE;
    }

    public boolean lostModeration() {
        return !gainedModeration;
    }

    @Override
    public <T> T accept(@NonNull MessageFromTwitchVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public static @NonNull Mode build(@NonNull AnswerBuilderHelper helper) {
        return Mode.builder()
                   .ircParsing(helper.getIrcParsing())
                   .channel(helper.channelFromParameterAt(0))
                   .gainedModeration(helper.mapParameter(1, s->s.startsWith("+")))
                   .user(helper.parameterAt(2))
                   .build();
    }

}
