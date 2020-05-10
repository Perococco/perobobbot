package bot.chat.advanced;

import lombok.NonNull;

import java.time.Duration;

/**
 * an {@link AdvancedChatIO} with method to control the state of the chat (started/stopped)
 * @param <M>
 */
public interface AdvancedChat<M> extends AdvancedChatIO<M> {

    /**
     * Start the chat. For instance, if the implementation
     * uses a websocket, this method will perform the connection
     * to the websocket
     */
    void start();

    /**
     * Request this chat to stop. This is a request and as such
     * this method should return immediately and there should be
     * no guarantee that the chat is stopped when returning.
     */
    void requestStop();

    /**
     * @return the timeout used when matching a request with its answer
     */
    @NonNull
    Duration getTimeout();

    /**
     * @param duration the timeout to used when matching a request with its answer
     */
    void setTimeout(@NonNull Duration duration);

}
