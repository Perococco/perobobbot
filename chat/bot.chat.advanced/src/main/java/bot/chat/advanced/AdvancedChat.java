package bot.chat.advanced;

import bot.common.lang.Subscription;
import lombok.NonNull;

import java.time.Duration;
import java.util.concurrent.CompletionStage;

/**
 * @author perococco
 **/
public interface AdvancedChat<M> {

    @NonNull
    CompletionStage<DispatchSlip> sendCommand(@NonNull Command command);

    @NonNull
    <A> CompletionStage<ReceiptSlip<A>> sendRequest(@NonNull Request<A> request);

    @NonNull
    Subscription addChatListener(@NonNull AdvancedChatListener<M> listener);

    @NonNull
    Duration timeout();

    void timeout(@NonNull Duration duration);
}
