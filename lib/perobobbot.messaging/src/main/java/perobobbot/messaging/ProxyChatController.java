package perobobbot.messaging;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import perobobbot.lang.MessageDispatcher;

@RequiredArgsConstructor
public class ProxyChatController implements ChatController {

    @NonNull
    @Delegate(types = {ChatController.class, MessageDispatcher.class})
    private final ChatController delegate;

}

