package perobobbot.chat.core;

import perobobbot.common.lang.Subscription;
import lombok.NonNull;

/**
 * The I/O part of the chat (sending and receiving message)
 * @author perococco
 **/
public interface ChatIO {

    /**
     * Send a message to the chat
     * @param message the message to send
     */
    void postMessage(@NonNull String message);

    /**
     * Add a listener that will be called every time an event occurs (error, connection, message received...).
     * @param listener the listener to add to the chat listener list
     * @return a subscription that can be used to remove the listener
     */
    @NonNull
    Subscription addChatListener(@NonNull ChatListener listener);

    /**
     * @return true if the chat is active
     */
    boolean isRunning();

}
