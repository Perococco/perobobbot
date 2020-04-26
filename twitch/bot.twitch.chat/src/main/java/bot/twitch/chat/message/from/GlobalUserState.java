package bot.twitch.chat.message.from;

import bot.common.irc.IRCParsing;
import bot.twitch.chat.message.IRCCommand;
import lombok.*;

@Builder
@RequiredArgsConstructor
@Getter
@ToString
public class GlobalUserState extends KnownMessageFromTwitch {

    @NonNull
    private final IRCParsing ircParsing;

    @Override
    public @NonNull IRCCommand command() {
        return IRCCommand.GLOBALUSERSTATE;
    }

    @Override
    public <T> T accept(@NonNull MessageFromTwitchVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public static GlobalUserState build(@NonNull AnswerBuilderHelper helper) {
        return GlobalUserState.builder()
                              .ircParsing(helper.ircParsing())
                              .build();
    }

}
