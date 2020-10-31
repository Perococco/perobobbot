package perobobbot.common.messaging;

import lombok.NonNull;
import perobobbot.common.lang.*;
import perococco.perobobbot.common.messaging.PerococcoChatControllerBuilder;

public interface ChatController {

    void handleMessage(@NonNull MessageContext messageContext);

    @NonNull
    Subscription addCommand(@NonNull String commandName, @NonNull Executor<? super ExecutionContext> handler);

    @NonNull
    Subscription addListener(@NonNull MessageHandler handler);

    @NonNull
    static ChatControllerBuilder builder() {
        return new PerococcoChatControllerBuilder();
    }
}
