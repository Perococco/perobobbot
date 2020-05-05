package bot.twitch.chat.message.from;

import bot.common.irc.IRCParsing;
import bot.twitch.chat.message.IRCCommand;
import lombok.*;

/**
 * @author perococco
 **/
@ToString
public class Welcome extends KnownMessageFromTwitch {

    @NonNull
    @Getter
    private final String userName;

    @Builder
    public Welcome(@NonNull IRCParsing ircParsing, @NonNull String userName) {
        super(ircParsing);
        this.userName = userName;
    }

    @Override
    public @NonNull IRCCommand command() {
        return IRCCommand.RPL_WELCOME;
    }

    @Override
    public <T> T accept(@NonNull MessageFromTwitchVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @NonNull
    public static Welcome build(@NonNull AnswerBuilderHelper helper) {
        return Welcome.builder()
                .ircParsing(helper.ircParsing())
                .userName(helper.parameterAt(0))
                .build();
    }
}
