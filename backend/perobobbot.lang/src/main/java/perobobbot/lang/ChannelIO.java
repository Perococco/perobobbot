package perobobbot.lang;

import lombok.NonNull;
import perobobbot.lang.fp.Function1;

/**
 * A IO (actually only O) to print message to an specific channel
 */
public interface ChannelIO {

    /**
     * Print a message to the channel this {@link ChannelIO} is associated to
     * @param messageBuilder the builder that can use the {@link DispatchContext} to create the message
     */
    void print(@NonNull Function1<? super DispatchContext, ? extends String> messageBuilder);

    /**
     * @param message the message to print to the channel this {@link ChannelIO} is associated to
     */
    default void print(@NonNull String message) {
        print(d -> message);
    }


}
