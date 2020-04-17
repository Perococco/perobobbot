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
public class Join extends MessageFromTwitchBase implements KnownMessageFromTwitch {

    @NonNull
    private final IRCParsing ircParsing;

    @NonNull
    private final String user;

    @NonNull
    private final Channel channel;

    @Override
    public @NonNull IRCCommand command() {
        return IRCCommand.JOIN;
    }


    public static @NonNull Join build(@NonNull AnswerBuilderHelper helper) {
        return Join.builder()
                   .ircParsing(helper.ircParsing())
                   .user(helper.userFromPrefix())
                   .channel(helper.channelFormParameterAt(0))
                   .build();
    }
}
