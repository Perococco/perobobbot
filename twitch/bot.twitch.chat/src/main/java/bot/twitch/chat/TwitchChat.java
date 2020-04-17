package bot.twitch.chat;

import bot.chat.core.ChatClient;
import bot.common.lang.Nil;
import bot.common.lang.Subscription;
import bot.twitch.chat.message.from.Join;
import bot.twitch.chat.message.from.Part;
import lombok.NonNull;
import perococco.bot.twitch.chat.PerococcoTwitchChat;

import java.util.concurrent.CompletionStage;

/**
 * @author perococco
 **/
public interface TwitchChat {

    @NonNull
    static TwitchChat create(@NonNull ChatClient chatClient) {
        return new PerococcoTwitchChat(chatClient);
    }

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
