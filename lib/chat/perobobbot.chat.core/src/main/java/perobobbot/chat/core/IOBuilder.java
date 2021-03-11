package perobobbot.chat.core;

import lombok.NonNull;
import perobobbot.lang.Platform;

public interface IOBuilder {

    @NonNull MutableIO build();

    @NonNull
    IOBuilder add(@NonNull ChatPlatform chatPlatform);
}
