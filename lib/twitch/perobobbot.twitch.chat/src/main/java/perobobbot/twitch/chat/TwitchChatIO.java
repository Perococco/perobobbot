package perobobbot.twitch.chat;

import lombok.NonNull;
import perobobbot.chat.core.ChannelIO;
import perobobbot.lang.DispatchContext;
import perobobbot.lang.MessageListener;
import perobobbot.lang.Subscription;
import perobobbot.lang.fp.Function1;

import java.net.URI;
import java.util.concurrent.CompletionStage;

/**
 * @author perococco
 **/
public interface TwitchChatIO {

    URI TWITCH_CHAT_URI = URI.create("wss://irc-ws.chat.twitch.tv:443");


    @NonNull CompletionStage<ChannelIO> join(@NonNull Channel channel);

    void partAll();

    /**
     * Send a message on the provided channel
     * @param channel the channel to send the message to
     * @param message the message to send
     * @return a completion stage that completes when the message has been sent
     */
    @NonNull
    default CompletionStage<TwitchDispatchSlip> send(@NonNull Channel channel, @NonNull String message) {
        return send(channel, d -> message);
    }

    @NonNull
    CompletionStage<TwitchDispatchSlip> send(@NonNull Channel channel, @NonNull Function1<? super DispatchContext, ? extends String> messageBuilder);

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

    default void send(@NonNull String channel, @NonNull Function1<? super DispatchContext, ? extends String> messageBuilder) {
        send(Channel.create(channel),messageBuilder);
    }


}
