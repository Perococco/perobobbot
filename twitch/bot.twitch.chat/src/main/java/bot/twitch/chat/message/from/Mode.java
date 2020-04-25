package bot.twitch.chat.message.from;

import bot.common.irc.IRCParsing;
import bot.twitch.chat.Channel;
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
public class Mode extends KnownMessageFromTwitch {

    @NonNull
    private final IRCParsing ircParsing;

    @NonNull
    private final Channel channel;

    @NonNull
    private final String user;

    private final boolean gainedModeration;

    @Override
    public @NonNull IRCCommand command() {
        return IRCCommand.MODE;
    }

    public boolean lostModeration() {
        return !gainedModeration;
    }

    @Override
    public void accept(@NonNull MessageFromTwitchVisitor visitor) {
        visitor.visit(this);
    }

    public static @NonNull Mode build(@NonNull AnswerBuilderHelper helper) {
        return Mode.builder()
                   .ircParsing(helper.ircParsing())
                   .channel(helper.channelFormParameterAt(0))
                   .gainedModeration(helper.mapParameter(1, s->s.startsWith("+")))
                   .user(helper.parameterAt(2))
                   .build();
    }

}
