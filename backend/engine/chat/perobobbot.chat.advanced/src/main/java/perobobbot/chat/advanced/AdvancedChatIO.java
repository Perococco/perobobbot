package perobobbot.chat.advanced;

import lombok.NonNull;
import perobobbot.common.lang.Subscription;

import java.util.concurrent.CompletionStage;

/**
 * @author perococco
 **/
public interface AdvancedChatIO<M> {

    /**
     * Send a command
     * @param command the command to send
     * @return a {@link CompletionStage} that completes when the command is sent
     */
    @NonNull
    CompletionStage<DispatchSlip> sendCommand(@NonNull Command command);

    /**
     * Send a request
     * @param request the request to send
     * @return a {@link CompletionStage} that completes when the answer to the request
     * is received
     */
    @NonNull
    <A> CompletionStage<ReceiptSlip<A>> sendRequest(@NonNull Request<A> request);

    /**
     * Add a listener that will received all event from this {@link AdvancedChatIO}
     * @param listener a listener to add
     * @return a subscription that can be used to remove the listener
     */
    @NonNull
    Subscription addChatListener(@NonNull AdvancedChatListener<M> listener);

    boolean isRunning();

}
