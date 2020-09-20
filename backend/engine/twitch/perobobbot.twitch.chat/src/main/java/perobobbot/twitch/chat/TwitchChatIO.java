package perobobbot.twitch.chat;

import lombok.NonNull;
import perobobbot.chat.advanced.DispatchContext;
import perobobbot.common.lang.Subscription;
import perobobbot.common.lang.fp.Consumer1;
import perobobbot.common.lang.fp.Function1;
import perobobbot.twitch.chat.event.ReceivedMessage;
import perobobbot.twitch.chat.message.from.PrivMsgFromTwitch;

import java.net.URI;
import java.util.concurrent.CompletionStage;

/**
 * @author perococco
 **/
public interface TwitchChatIO {

    URI TWITCH_CHAT_URI = URI.create("wss://irc-ws.chat.twitch.tv:443");

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


}
