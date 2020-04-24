package bot.twitch.chat;

import bot.twitch.chat.message.from.GlobalUserState;
import bot.twitch.chat.message.from.Part;
import bot.twitch.chat.message.from.UserState;
import bot.twitch.chat.message.to.OAuthResult;
import lombok.NonNull;
import perococco.bot.twitch.chat.PerococcoTwitchChat;

import java.net.URI;
import java.util.concurrent.CompletionStage;

public interface TwitchChatManager extends TwitchChat {

    @NonNull
    static TwitchChatManager create() {
        return create(TWITCH_CHAT_URI);
    }

    @NonNull
    static TwitchChatManager create(@NonNull URI uri) {
        return new PerococcoTwitchChat(uri);
    }


    /**
     * Join a channel
     * @param channel the channel to join
     * @return a completion stage that completes when the join request is answered
     */
    @NonNull
    CompletionStage<TwitchReceiptSlip<UserState>> join(@NonNull Channel channel);

    /**
     * leave a channel
     * @param channel the channel to leave
     * @return a completion stage that completes when the part request is answered
     */
    @NonNull
    CompletionStage<TwitchReceiptSlip<Part>> part(@NonNull Channel channel);


    @NonNull
    default CompletionStage<TwitchReceiptSlip<UserState>> join(@NonNull String channelName) {
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


    @NonNull
    CompletionStage<TwitchReceiptSlip<GlobalUserState>> start(@NonNull TwitchChatOAuth oAuth);

    void requestStop();
}
