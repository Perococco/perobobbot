package perobobbot.chat.core;

import lombok.NonNull;
import perobobbot.lang.Platform;

public interface IOBuilder {

    IO build();

    @NonNull
    IOBuilder add(@NonNull Platform platform, @NonNull ChatPlatform chatPlatform);
}
