package perobobbot.lang;

import lombok.NonNull;
import perobobbot.lang.fp.Function1;

public interface PlatformIO {

    @NonNull Platform getPlatform();

    /**
     * Print a message to the a given channel
     * @param channel the channel to send the message to
     * @param messageBuilder the builder that can use the {@link DispatchContext} to create the message
     */
    void print(@NonNull String channel, @NonNull Function1<? super DispatchContext, ? extends String> messageBuilder);

    @NonNull Subscription addMessageListener(@NonNull MessageListener listener);

    /**
     * @param channel the channel to send the message to
     * @param message print the message to the IO that received the message that trigger the program
     */
    default void print(@NonNull String channel , @NonNull String message) {
        print(channel, d -> message);
    }

    /**
     * @param channel the channel the returned {@link ChannelIO} is linked to
     * @return a {@link ChannelIO} linked to the provided channel
     */
    @NonNull
    default ChannelIO forChannel(@NonNull String channel) {
        return messageBuilder -> PlatformIO.this.print(channel, messageBuilder);
    }

}
