package perobobbot.chat.core;

/**
 * @author perococco
 */
public interface Chat extends ChatIO {

    /**
     * Start the chat. Should return
     * when the chat is actually started and
     * message can be sent without error
     */
    void start();

    /**
     * Request the chat to stop.
     * should return immediately
     */
    void requestStop();

}
