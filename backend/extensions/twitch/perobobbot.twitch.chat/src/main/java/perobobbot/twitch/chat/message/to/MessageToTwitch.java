package perobobbot.twitch.chat.message.to;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import perobobbot.chat.advanced.Message;
import perobobbot.lang.DispatchContext;

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
        return "MessageToTwitch{" + payload(DispatchContext.NIL) + "}";
    }
}
