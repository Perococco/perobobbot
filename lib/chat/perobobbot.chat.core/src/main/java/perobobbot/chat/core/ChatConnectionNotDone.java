package perobobbot.chat.core;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import perobobbot.lang.Bot;
import perobobbot.lang.Platform;

@Getter
@ToString
public class ChatConnectionNotDone extends ChatException {

    private final @NonNull Platform platform;
    private final @NonNull Bot bot;

    public ChatConnectionNotDone(@NonNull Platform platform, @NonNull Bot bot) {
        super("No connection has been done on the chat platform '"+platform+"' for the bot '"+ bot +"'");
        this.platform = platform;
        this.bot = getBot();
    }
}
