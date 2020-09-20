package perobobbot.chat.core;

/**
 * @author perococco
 */
public interface Chat extends ChatIO {

    /**
     * Start the chat. must return
     * when the chat is actually started and
     * messages can be sent without error
     */
    void start();

    /**
     * Request the chat to stop.
     * should return immediately.
     * There is not guaranty that the chat is actually stop when returning.
     */
    void requestStop();

}
