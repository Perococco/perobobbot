package perobobbot.twitch.chat.message.to;

import perobobbot.chat.advanced.Command;
import perobobbot.twitch.chat.message.IRCCommand;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author perococco
 **/
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public abstract class CommandToTwitch extends MessageToTwitch implements Command {

    @NonNull
    @Getter
    private final IRCCommand command;

    @Override
    public String commandInPayload() {
        return command.name();
    }

}
