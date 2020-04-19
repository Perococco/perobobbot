package bot.twitch.chat.message.from;

import bot.common.irc.IRCParsing;
import bot.twitch.chat.message.IRCCommand;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
@Getter
public class GlobalUserState implements KnownMessageFromTwitch {

    @NonNull
    private final IRCParsing ircParsing;

    @Override
    public @NonNull IRCCommand command() {
        return IRCCommand.GLOBALUSERSTATE;
    }

    public static GlobalUserState build(@NonNull AnswerBuilderHelper helper) {
        return GlobalUserState.builder()
                              .ircParsing(helper.ircParsing())
                              .build();
    }
}
