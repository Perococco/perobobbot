package perobobbot.chat.core;

import lombok.NonNull;
import perobobbot.lang.*;
import perobobbot.lang.fp.Function1;
import perococco.perobobbot.chat.core.PerococcoIOBuilder;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

public interface IO {

    /**
     * Print a message to the provided channel
     * @param connectionInfo the connection information to use to send the message, he must have enable a connection with the chat beforehand
     * @param channelName the name of the channel to send the message to
     * @param messageBuilder abuilder that can use the {@link DispatchContext} to create the message to send
     */
    @NonNull
    CompletionStage<? extends DispatchSlip> send(@NonNull ConnectionInfo connectionInfo, @NonNull String channelName, @NonNull Function1<? super DispatchContext, ? extends String> messageBuilder);

    /**
     * @param connectionInfo the connection information to use to send the message, he must have enable a connection with the chat beforehand
     * @param channelName the name of the channel to send the message to
     * @param message print the message to the provided channel
     */
    default void send(@NonNull ConnectionInfo connectionInfo, @NonNull String channelName , @NonNull String message) {
        send(connectionInfo, channelName, d -> message);
    }

    @NonNull
    Optional<ChatPlatform> findPlatform(@NonNull Platform platform);

    @NonNull
    default ChatPlatform getPlatform(@NonNull Platform platform) {
        return findPlatform(platform).orElseThrow(() -> new IllegalArgumentException("No IO defined for platform '"+platform+"'"));
    }

    default @NonNull CompletionStage<? extends MessageChannelIO> getMessageChannelIO(@NonNull ConnectionInfo connectionInfo, @NonNull ChannelInfo channelInfo) {
        return getPlatform(channelInfo.getPlatform()).getChannelIO(connectionInfo, channelInfo.getChannelName());
    }


    static IOBuilder builder() {
        return new PerococcoIOBuilder();
    }
}
