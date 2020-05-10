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
