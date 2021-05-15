package perobobbot.chat.core;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

@RequiredArgsConstructor
public class ProxyChatConnection implements ChatConnection{

    @Delegate
    private final @NonNull ChatConnection delegate;

}
