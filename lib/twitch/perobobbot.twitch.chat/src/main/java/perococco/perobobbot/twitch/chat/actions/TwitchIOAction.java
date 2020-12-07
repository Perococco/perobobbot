package perococco.perobobbot.twitch.chat.actions;

import lombok.NonNull;
import perococco.perobobbot.twitch.chat.TwitchIO;

import java.util.concurrent.CompletionStage;

public interface TwitchIOAction<T> {

    @NonNull
    CompletionStage<T> evaluate(@NonNull TwitchIO io);
}
