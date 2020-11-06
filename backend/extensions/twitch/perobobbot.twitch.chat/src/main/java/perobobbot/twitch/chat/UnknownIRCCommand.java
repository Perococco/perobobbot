package perobobbot.twitch.chat;

import lombok.Getter;
import lombok.NonNull;

public class UnknownIRCCommand extends TwitchChatException {

    @NonNull
    @Getter
    private final String commandInPayload;

    public UnknownIRCCommand(@NonNull String commandInPayload) {
        this.commandInPayload = commandInPayload;
    }
}
