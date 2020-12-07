package perobobbot.lang;

import lombok.NonNull;

/**
 * Listen to incoming message.
 */
public interface MessageListener {

    /**
     * @param messageContext the incoming message
     */
    void onMessage(@NonNull MessageContext messageContext);
}
