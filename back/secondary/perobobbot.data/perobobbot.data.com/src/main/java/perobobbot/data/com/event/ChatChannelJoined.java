package perobobbot.data.com.event;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.lang.Platform;

import java.util.UUID;

public class ChatChannelJoined extends ChatEvent {

    @Getter
    private final @NonNull UUID botId;
    @Getter
    private final @NonNull String channelName;

    public ChatChannelJoined(@NonNull Platform platform, @NonNull UUID botId, @NonNull String channelName) {
        super(platform);
        this.botId = botId;
        this.channelName = channelName;
    }
}
