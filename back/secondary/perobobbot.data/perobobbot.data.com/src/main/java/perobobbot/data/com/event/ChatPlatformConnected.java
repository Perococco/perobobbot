package perobobbot.data.com.event;

import lombok.NonNull;
import perobobbot.lang.Platform;

public class ChatPlatformConnected extends ChatEvent {

    public ChatPlatformConnected(@NonNull Platform platform) {
        super(platform);
    }
}
