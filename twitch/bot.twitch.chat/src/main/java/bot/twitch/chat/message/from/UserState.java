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
@Getter
@RequiredArgsConstructor
@Builder
public class UserState implements KnownMessageFromTwitch {


    @NonNull
    private final IRCParsing ircParsing;

    @NonNull
    private final Channel channel;

    @Override
    public @NonNull IRCCommand command() {
        return IRCCommand.USERSTATE;
    }

    public static UserState build(@NonNull AnswerBuilderHelper helper) {
        return UserState.builder()
                        .ircParsing(helper.ircParsing())
                        .channel(helper.channelFormParameterAt(0))
                        .build();
    }

}
