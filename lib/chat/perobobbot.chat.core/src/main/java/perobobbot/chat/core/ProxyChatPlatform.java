package perobobbot.chat.core;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

@RequiredArgsConstructor
public class ProxyChatPlatform implements ChatPlatform {

    @Delegate
    private final @NonNull ChatPlatform delegate;

}
