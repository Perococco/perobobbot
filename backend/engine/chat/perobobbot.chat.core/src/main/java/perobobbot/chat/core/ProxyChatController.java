package perobobbot.chat.core;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

@RequiredArgsConstructor
public class ProxyChatController implements ChatController {

    @NonNull
    @Delegate
    private final ChatController delegate;

}

