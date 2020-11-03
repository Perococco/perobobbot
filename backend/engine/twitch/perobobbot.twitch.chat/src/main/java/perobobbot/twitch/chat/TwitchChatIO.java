package perobobbot.twitch.chat;

import lombok.NonNull;
import perobobbot.common.lang.*;
import perobobbot.common.lang.fp.Function1;

import java.net.URI;
import java.util.concurrent.CompletionStage;

/**
 * @author perococco
 **/
public interface TwitchChatIO extends PlatformIO {

    URI TWITCH_CHAT_URI = URI.create("wss://irc-ws.chat.twitch.tv:443");

    @Override
    default @NonNull Platform getPlatform() {
        return Platform.TWITCH;
    }

    /**
     * Send a message on the provided channel
     * @param channel the channel to send the message to
     * @param message the message to send
     * @return a completion stage that completes when the message has been sent
     */
    @NonNull
    default CompletionStage<TwitchDispatchSlip> message(@NonNull Channel channel, @NonNull String message) {
        return message(channel,d -> message);
    }

    @NonNull
    CompletionStage<TwitchDispatchSlip> message(@NonNull Channel channel, @NonNull Function1<? super DispatchContext, ? extends String> messageBuilder);

    @Override
    @NonNull
    default Subscription addMessageListener(@NonNull MessageListener listener) {
        return addPrivateMessageListener(message -> message.toMessage().ifPresent(listener::onMessage));
    }

    /**
     * Add a listener of event that occurs on the Twitch chat
     * @param listener the listener to add
     * @return a subscription that can be used to remove the listener
     */
    @NonNull
    Subscription addTwitchChatListener(@NonNull TwitchChatListener listener);

    @NonNull
    default Subscription addPrivateMessageListener(@NonNull PrivMsgFromTwitchListener listener) {
        return this.addTwitchChatListener(listener.toTwitchChatListener());
    }

    /**
     * @return true if this TwitchChat is running
     */
    boolean isRunning();

    @Override
    default void print(@NonNull String channel, @NonNull Function1<? super DispatchContext, ? extends String> messageBuilder) {
        message(Channel.create(channel),messageBuilder);
    }


}
