package perobobbot.chat.core;

import lombok.NonNull;
import perobobbot.lang.ChannelInfo;
import perobobbot.lang.DispatchContext;
import perobobbot.lang.Nil;
import perobobbot.lang.Platform;
import perobobbot.lang.fp.Function1;
import perococco.perobobbot.chat.core.PerococcoIOBuilder;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

public interface IO {

    /**
     * Print a message to the provided channel
     * @param nick the user that will send the message, he must have enable a connection with the chat beforehand
     * @param channelInfo the channel information to find the platform and the channel to send the message to
     * @param messageBuilder abuilder that can use the {@link DispatchContext} to create the message to send
     */
    @NonNull
    CompletionStage<? extends DispatchSlip> send(@NonNull String nick, @NonNull ChannelInfo channelInfo, @NonNull Function1<? super DispatchContext, ? extends String> messageBuilder);

    /**
     * @param nick the user that will send the message, he must have enable a connection with the chat beforehand
     * @param channelInfo the channel information to find the platform and the channel to send the message to
     * @param message print the message to the provided channel
     */
    default void send(@NonNull String nick, @NonNull ChannelInfo channelInfo , @NonNull String message) {
        send(nick, channelInfo, d -> message);
    }

    @NonNull
    Optional<ChatPlatform> findPlatform(@NonNull Platform platform);

    @NonNull
    default ChatPlatform getPlatform(@NonNull Platform platform) {
        return findPlatform(platform).orElseThrow(() -> new IllegalArgumentException("No IO defined for platform '"+platform+"'"));
    }


    static IOBuilder builder() {
        return new PerococcoIOBuilder();
    }
}
