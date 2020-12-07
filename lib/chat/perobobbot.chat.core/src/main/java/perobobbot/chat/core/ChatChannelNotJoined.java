package perobobbot.chat.core;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import perobobbot.lang.Platform;

@Getter
@ToString
public class ChatChannelNotJoined extends ChatException {

    private final @NonNull Platform platform;
    private final @NonNull String nick;
    private final @NonNull String channelName;

    public ChatChannelNotJoined(
            @NonNull Platform platform,
            @NonNull String nick,
            @NonNull String channelName) {
        super("The user '"+nick+"' has not joined the channel '"+channelName+"' on platfrom '"+platform+"'");
        this.platform = platform;
        this.nick = nick;
        this.channelName = channelName;
    }
}
