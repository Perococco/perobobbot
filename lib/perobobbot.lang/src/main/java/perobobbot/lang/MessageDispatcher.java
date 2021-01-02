package perobobbot.lang;

import lombok.NonNull;

public interface MessageDispatcher {

    @NonNull Subscription addListener(@NonNull MessageHandler messageHandler);

}
