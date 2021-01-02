package perobobbot.chat.core;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import perobobbot.lang.ChatConnectionInfo;

@Getter
@ToString
public class ChatChannelNotJoined extends ChatException {

    private final @NonNull ChatConnectionInfo chatConnectionInfo;
    private final @NonNull String channelName;

    public ChatChannelNotJoined(
            @NonNull ChatConnectionInfo chatConnectionInfo,
            @NonNull String channelName) {
        super("The bot '"+ chatConnectionInfo.getBotName()+"' has not joined the channel '"+channelName+"' on platform '"+ chatConnectionInfo.getPlatform()+"'");
        this.chatConnectionInfo = chatConnectionInfo;
        this.channelName = channelName;
    }
}
