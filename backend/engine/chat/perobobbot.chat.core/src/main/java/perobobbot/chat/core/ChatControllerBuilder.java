package perobobbot.chat.core;

import lombok.NonNull;
import perobobbot.common.lang.Platform;

public interface ChatControllerBuilder {

    @NonNull
    ChatControllerBuilder setCommandPrefix(char prefix);

    @NonNull
    ChatControllerBuilder setCommandPrefix(@NonNull Platform platform, char prefix);

    @NonNull
    ChatController build();
}
