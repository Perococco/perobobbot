package bot.twitch.chat.message;

import bot.chat.advanced.Command;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author perococco
 **/
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public abstract class TwitchCommand extends TwitchMessage implements Command {

    @NonNull
    @Getter
    private final IRCCommand command;

}
