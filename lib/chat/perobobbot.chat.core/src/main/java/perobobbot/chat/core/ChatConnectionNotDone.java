package perobobbot.chat.core;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import perobobbot.lang.Platform;

@Getter
@ToString
public class ChatConnectionNotDone extends ChatException {

    private final @NonNull Platform platform;
    private final @NonNull String nick;

    public ChatConnectionNotDone(@NonNull Platform platform, @NonNull String nick) {
        super("No connection has been done on the chat platform '"+platform+"' with the user '"+nick+"'");
        this.platform = platform;
        this.nick = nick;
    }
}
