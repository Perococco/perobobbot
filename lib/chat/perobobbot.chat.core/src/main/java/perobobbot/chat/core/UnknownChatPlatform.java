package perobobbot.chat.core;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.lang.Platform;

public class UnknownChatPlatform extends ChatException {

    @Getter
    private final @NonNull Platform platform;

    public UnknownChatPlatform(@NonNull Platform platform) {
        super("Not chat platform defined for '"+platform);
        this.platform = platform;
    }
}
