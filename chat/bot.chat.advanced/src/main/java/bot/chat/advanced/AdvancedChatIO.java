package bot.chat.advanced;

import bot.common.lang.Subscription;
import lombok.NonNull;

import java.util.concurrent.CompletionStage;

/**
 * @author perococco
 **/
public interface AdvancedChatIO<M> {

    @NonNull
    CompletionStage<DispatchSlip> sendCommand(@NonNull Command command);

    @NonNull
    <A> CompletionStage<ReceiptSlip<A>> sendRequest(@NonNull Request<A> request);

    @NonNull
    Subscription addChatListener(@NonNull AdvancedChatListener<M> listener);

    boolean isRunning();

}
