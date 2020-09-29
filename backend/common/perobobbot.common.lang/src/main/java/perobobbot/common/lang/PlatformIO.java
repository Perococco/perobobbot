package perobobbot.common.lang;

import lombok.NonNull;
import perobobbot.common.lang.fp.Function1;

public interface PlatformIO {

    /**
     * Print a message to the IO that received the message that trigger the program
     * @param channel the channel to send the message to
     * @param messageBuilder the builder that can use the {@link DispatchContext} to create the message
     */
    void print(@NonNull String channel, @NonNull Function1<? super DispatchContext, ? extends String> messageBuilder);

    /**
     * @param channel the channel to send the message to
     * @param message print the message to the IO that received the message that trigger the program
     */
    default void print(@NonNull String channel , @NonNull String message) {
        print(channel, d -> message);
    }

    default ChannelIO forChannel(@NonNull String channel) {
        return m -> PlatformIO.this.print(channel,m);
    }

}
