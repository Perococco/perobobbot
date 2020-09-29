package perobobbot.twitch.chat;

import lombok.NonNull;
import perococco.perobobbot.twitch.chat.PerococcoTwitchChat;

import java.net.URI;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

public interface TwitchChat extends TwitchChatIO {

    @NonNull
    static TwitchChat create(@NonNull TwitchChatOptions options) {
        return create(TWITCH_CHAT_URI,options);
    }

    @NonNull
    static TwitchChat create(@NonNull URI uri, @NonNull TwitchChatOptions options) {
        return new PerococcoTwitchChat(uri,options);
    }

    @NonNull
    CompletionStage<TwitchChatIO> start();

    default TwitchChatIO startAndWait() throws ExecutionException, InterruptedException {
        return start().toCompletableFuture().get();
    }

    void requestStop();
}
