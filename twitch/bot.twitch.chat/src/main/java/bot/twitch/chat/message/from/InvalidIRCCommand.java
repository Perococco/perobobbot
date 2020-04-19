package bot.twitch.chat.message.from;

import bot.common.irc.IRCParsing;
import bot.twitch.chat.message.IRCCommand;
import lombok.*;

@RequiredArgsConstructor
@Builder
@ToString
public class InvalidIRCCommand implements KnownMessageFromTwitch {

    @NonNull
    @Getter
    private final IRCParsing ircParsing;

    @NonNull
    @Getter
    private final String user;

    @NonNull
    @Getter
    private final String requestedCommand;
    
    @Override
    public @NonNull IRCCommand command() {
        return IRCCommand.ERR_UNKNOWNCOMMAND;
    }

    @NonNull
    public static InvalidIRCCommand build(@NonNull AnswerBuilderHelper helper) {
        return InvalidIRCCommand.builder()
                .ircParsing(helper.ircParsing())
                .user(helper.parameterAt(0))
                .requestedCommand(helper.parameterAt(1))
                .build();
    }
}
