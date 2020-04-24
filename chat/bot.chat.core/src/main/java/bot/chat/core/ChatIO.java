package bot.chat.core;

import bot.common.lang.Subscription;
import lombok.NonNull;

/**
 * @author perococco
 **/
public interface ChatIO {

    /**
     * Send a message to the chat
     * @param message the message to send
     */
    void postMessage(@NonNull String message);

    /**
     * Add a listener that will be called every time a message is received from or sent to the chat.
     * @param listener the listener to add to the chat listener list
     * @return a subscription that can be used to remove the listener
     */
    @NonNull
    Subscription addChatListener(@NonNull ChatListener listener);

    boolean isRunning();

}
