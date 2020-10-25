package perobobbot.common.lang;

import lombok.NonNull;

public interface MessageHandler {

    boolean handleMessage(@NonNull MessageContext messageHandler);
}
