package perobobbot.chat.core;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import perobobbot.lang.ConnectionInfo;

@Getter
@ToString
public class ChatChannelNotJoined extends ChatException {

    private final @NonNull ConnectionInfo connectionInfo;
    private final @NonNull String channelName;

    public ChatChannelNotJoined(
            @NonNull ConnectionInfo connectionInfo,
            @NonNull String channelName) {
        super("The bot '"+connectionInfo.getBotName()+"' has not joined the channel '"+channelName+"' on platform '"+connectionInfo.getPlatform()+"'");
        this.connectionInfo = connectionInfo;
        this.channelName = channelName;
    }
}
