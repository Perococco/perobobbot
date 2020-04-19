package bot.twitch.chat;

import bot.common.lang.Subscription;
import bot.twitch.chat.message.from.Join;
import bot.twitch.chat.message.from.Part;
import lombok.NonNull;

import java.net.URI;
import java.util.concurrent.CompletionStage;

/**
 * @author perococco
 **/
public interface TwitchChat {

    URI TWITCH_CHAT_URI = URI.create("wss://irc-ws.chat.twitch.tv:443");

    /**
     * Join a channel
     * @param channel the channel to join
     * @return a completion stage that completes when the join request is answered
     */
    @NonNull
    CompletionStage<TwitchReceiptSlip<Join>> join(@NonNull Channel channel);

    /**
     * leave a channel
     * @param channel the channel to leave
     * @return a completion stage that completes when the part request is answered
     */
    @NonNull
    CompletionStage<TwitchReceiptSlip<Part>> part(@NonNull Channel channel);

    /**
     * Send a message on the provided channel
     * @param channel the channel to send the message to
     * @param message the message to send
     * @return a completion stage that completes when the message has been sent
     */
    @NonNull
    CompletionStage<TwitchDispatchSlip> message(@NonNull Channel channel, @NonNull String message);

    Subscription addTwitchChatListener(@NonNull TwitchChatListener listener);

    boolean isRunning();

    @NonNull
    default CompletionStage<TwitchReceiptSlip<Join>> join(@NonNull String channelName) {
        return join(Channel.create(channelName));
    }

    @NonNull
    default CompletionStage<TwitchReceiptSlip<Part>> part(@NonNull String channelName) {
        return part(Channel.create(channelName));
    }

    @NonNull
    default CompletionStage<TwitchDispatchSlip> message(@NonNull String channelName, @NonNull String message) {
        return message(Channel.create(channelName),message);
    }


}
