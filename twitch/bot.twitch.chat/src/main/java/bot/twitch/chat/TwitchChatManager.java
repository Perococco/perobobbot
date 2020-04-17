package bot.twitch.chat;

import bot.twitch.chat.message.to.OAuthResult;
import lombok.NonNull;

import java.util.concurrent.CompletionStage;

public interface TwitchChatManager extends TwitchChat {

    @NonNull
    CompletionStage<TwitchReceiptSlip<OAuthResult>> connect(@NonNull TwitchChatOAuth oAuth);

    void disconnect();
}
