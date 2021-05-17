package perobobbot.chat.core;

import lombok.NonNull;

public interface IOBuilder {

    @NonNull MutableIO build();

    @NonNull
    IOBuilder add(@NonNull ChatPlatform chatPlatform);
}
