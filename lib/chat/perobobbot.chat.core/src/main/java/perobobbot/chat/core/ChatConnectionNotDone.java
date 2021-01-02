package perobobbot.chat.core;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import perobobbot.lang.Bot;
import perobobbot.lang.ConnectionInfo;
import perobobbot.lang.Platform;

@Getter
@ToString
public class ChatConnectionNotDone extends ChatException {

    private final @NonNull ConnectionInfo connectionInfo;

    public ChatConnectionNotDone(@NonNull ConnectionInfo connectionInfo) {
        super("No connection has been done on the chat platform '"+connectionInfo.getPlatform()+"' for the bot '"+ connectionInfo.getBotName() +"'");
        this.connectionInfo = connectionInfo;
    }
}
