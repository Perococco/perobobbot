package perobobbot.common.messaging;

import lombok.NonNull;
import perobobbot.common.lang.Subscription;

public interface CommandBundle {

    @NonNull
    Subscription attachCommandsToChat();

}
