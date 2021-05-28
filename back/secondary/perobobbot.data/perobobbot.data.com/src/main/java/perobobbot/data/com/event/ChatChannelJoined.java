package perobobbot.data.com.event;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.lang.ViewerIdentity;

import java.util.UUID;

public class ChatChannelJoined extends ChatEvent {

    @Getter
    private final @NonNull UUID botId;
    @Getter
    private final @NonNull ViewerIdentity viewerIdentity;
    @Getter
    private final @NonNull String channelName;

    public ChatChannelJoined(@NonNull UUID botId,
                             @NonNull ViewerIdentity viewerIdentity,
                             @NonNull String channelName) {
        super(viewerIdentity.getPlatform());
        this.botId = botId;
        this.channelName = channelName;
        this.viewerIdentity = viewerIdentity;
    }

    public @NonNull UUID getViewerIdentityId() {
        return viewerIdentity.getId();
    }
}
