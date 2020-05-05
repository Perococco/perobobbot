package bot.twitch.chat.message.from;

import bot.common.irc.IRCParsing;
import bot.twitch.chat.message.IRCCommand;
import lombok.*;

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
    public @NonNull IRCCommand command() {
        return IRCCommand.ERR_UNKNOWNCOMMAND;
    }

    @Override
    public <T> T accept(@NonNull MessageFromTwitchVisitor<T> visitor) {
        return visitor.visit(this);
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
