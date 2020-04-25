package bot.twitch.chat.message.to;

import bot.chat.advanced.Message;
import bot.twitch.chat.message.IRCCommand;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * @author perococco
 **/
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public abstract class MessageToTwitch implements Message {

    public abstract String commandInPayload();

    @Override
    public String toString() {
        if (commandInPayload().equalsIgnoreCase("PASS")) {
            return "MessageToTwitch{PASS *****}";
        }
        return "MessageToTwitch{"+payload()+"}";
    }
}
