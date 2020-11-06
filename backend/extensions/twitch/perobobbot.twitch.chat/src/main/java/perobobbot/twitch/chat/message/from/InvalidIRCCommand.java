package perobobbot.twitch.chat.message.from;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import perobobbot.common.irc.IRCParsing;
import perobobbot.twitch.chat.message.IRCCommand;

@ToString
public class InvalidIRCCommand extends KnownMessageFromTwitch {

    @NonNull
    @Getter
    private final String user;

    @NonNull
    @Getter
    private final String requestedCommand;

    @Builder
    public InvalidIRCCommand(@NonNull IRCParsing ircParsing, @NonNull String user, @NonNull String requestedCommand) {
        super(ircParsing);
        this.user = user;
        this.requestedCommand = requestedCommand;
    }

    @Override
    public @NonNull IRCCommand getCommand() {
        return IRCCommand.ERR_UNKNOWNCOMMAND;
    }

    @Override
    public <T> T accept(@NonNull MessageFromTwitchVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @NonNull
    public static InvalidIRCCommand build(@NonNull AnswerBuilderHelper helper) {
        return InvalidIRCCommand.builder()
                .ircParsing(helper.getIrcParsing())
                .user(helper.parameterAt(0))
                .requestedCommand(helper.parameterAt(1))
                .build();
    }

}
