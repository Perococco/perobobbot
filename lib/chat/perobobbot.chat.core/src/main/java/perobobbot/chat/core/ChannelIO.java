package perobobbot.chat.core;

import lombok.NonNull;
import perobobbot.lang.DispatchContext;
import perobobbot.lang.MessageListener;
import perobobbot.lang.Subscription;
import perobobbot.lang.fp.Function1;

import java.util.concurrent.CompletionStage;

/**
 * A IO (actually only O) to print message to an specific channel
 */
public interface ChannelIO {

    /**
     * send a message to the channel this {@link ChannelIO} is associated to
     * @param messageBuilder the builder that can use the {@link DispatchContext} to create the message
     */
    CompletionStage<? extends DispatchSlip> send(@NonNull Function1<? super DispatchContext, ? extends String> messageBuilder);

    /**
     * @param message the message to send to the channel this {@link ChannelIO} is associated to
     */
    default CompletionStage<? extends DispatchSlip> send(@NonNull String message) {
        return send(d -> message);
    }

    @NonNull Subscription addMessageListener(@NonNull MessageListener listener);

}
