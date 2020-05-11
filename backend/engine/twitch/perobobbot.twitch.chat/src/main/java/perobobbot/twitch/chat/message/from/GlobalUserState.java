package perobobbot.twitch.chat.message.from;

import perobobbot.common.irc.IRCParsing;
import perobobbot.twitch.chat.message.IRCCommand;
import lombok.*;

@Getter
@ToString
public class GlobalUserState extends KnownMessageFromTwitch {

    @Builder
    public GlobalUserState(@NonNull IRCParsing ircParsing) {
        super(ircParsing);
    }

    @Override
    public @NonNull IRCCommand getCommand() {
        return IRCCommand.GLOBALUSERSTATE;
    }

    @Override
    public <T> T accept(@NonNull MessageFromTwitchVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public static GlobalUserState build(@NonNull AnswerBuilderHelper helper) {
        return GlobalUserState.builder()
                              .ircParsing(helper.getIrcParsing())
                              .build();
    }

}
