package perobobbot.twitch.chat.message.from;

import perobobbot.common.irc.IRCParsing;
import perobobbot.twitch.chat.message.IRCCommand;
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
    public @NonNull IRCCommand getCommand() {
        return IRCCommand.RPL_WELCOME;
    }

    @Override
    public <T> T accept(@NonNull MessageFromTwitchVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @NonNull
    public static Welcome build(@NonNull AnswerBuilderHelper helper) {
        return Welcome.builder()
                .ircParsing(helper.getIrcParsing())
                .userName(helper.parameterAt(0))
                .build();
    }
}
