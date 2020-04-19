package bot.twitch.chat;

import bot.twitch.chat.message.from.GlobalUserState;
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

    @NonNull
    CompletionStage<TwitchReceiptSlip<GlobalUserState>> start(@NonNull TwitchChatOAuth oAuth);

    void requestStop();
}
