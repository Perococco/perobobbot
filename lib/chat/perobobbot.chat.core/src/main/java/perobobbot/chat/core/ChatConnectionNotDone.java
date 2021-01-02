package perobobbot.chat.core;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import perobobbot.lang.ChatConnectionInfo;

@Getter
@ToString
public class ChatConnectionNotDone extends ChatException {

    private final @NonNull ChatConnectionInfo chatConnectionInfo;

    public ChatConnectionNotDone(@NonNull ChatConnectionInfo chatConnectionInfo) {
        super("No connection has been done on the chat platform '"+ chatConnectionInfo.getPlatform()+"' for the bot '"+ chatConnectionInfo.getBotName() +"'");
        this.chatConnectionInfo = chatConnectionInfo;
    }
}
