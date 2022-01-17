package perobobbot.data.com.event;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.lang.Platform;

import java.util.UUID;

public class ChatChannelJoined extends ChatEvent {

    @Getter
    private final @NonNull UUID platformBotId;
    @Getter
    private final @NonNull String channelName;

    public ChatChannelJoined(@NonNull UUID platformBotId,
                             @NonNull Platform platform,
                             @NonNull String channelName) {
        super(platform);
        this.platformBotId = platformBotId;
        this.channelName = channelName;
    }

}
