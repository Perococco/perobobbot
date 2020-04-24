package bot.twitch.chat.message.from;

import bot.common.irc.IRCParsing;
import bot.twitch.chat.Channel;
import bot.twitch.chat.message.IRCCommand;
import lombok.*;

/**
 * @author perococco
 **/
@RequiredArgsConstructor
@Builder
@Getter
@ToString(exclude = "ircParsing")
public class Join implements KnownMessageFromTwitch {

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
