package perobobbot.data.com.event;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.lang.Platform;

import java.util.UUID;

public class ChatChannelJoined extends ChatEvent {

    @Getter
    private final @NonNull UUID botId;
    private final @NonNull UUID viewerIdentityId;
    @Getter
    private final @NonNull String channelName;

    public ChatChannelJoined(@NonNull UUID botId,
                             @NonNull UUID viewerIdentityId,
                             @NonNull Platform platform,
                             @NonNull String channelName) {
        super(platform);
        this.botId = botId;
        this.channelName = channelName;
        this.viewerIdentityId = viewerIdentityId;
    }

    public @NonNull UUID getViewerIdentityId() {
        return viewerIdentityId;
    }
}
