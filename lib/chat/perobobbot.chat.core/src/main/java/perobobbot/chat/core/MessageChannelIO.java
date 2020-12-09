package perobobbot.chat.core;

import lombok.NonNull;
import perobobbot.lang.DispatchContext;
import perobobbot.lang.MessageListener;
import perobobbot.lang.Subscription;
import perobobbot.lang.fp.Function1;

import java.util.concurrent.CompletionStage;

/**
 * A I/O to print message to an specific channel
 */
public interface MessageChannelIO {

    /**
     * send a message to the channel this {@link MessageChannelIO} is associated to
     * @param messageBuilder the builder that can use the {@link DispatchContext} to create the message
     * @return a {@link CompletionStage} that will return in completion the {@link DispatchSlip} associated with the message
     */
    @NonNull CompletionStage<? extends DispatchSlip> send(@NonNull Function1<? super DispatchContext, ? extends String> messageBuilder);

    /**
     * @param message the message to send to the channel this {@link MessageChannelIO} is associated to
     * @return a {@link CompletionStage} that will return in completion the {@link DispatchSlip} associated with the message
     */
    default @NonNull CompletionStage<? extends DispatchSlip> send(@NonNull String message) {
        return send(d -> message);
    }

    @NonNull Subscription addMessageListener(@NonNull MessageListener listener);

}
