package perococco.bot.twitch.chat.actions;

import lombok.NonNull;
import perococco.bot.twitch.chat.IO;

import java.util.concurrent.CompletionStage;

public interface IOAction<T> {

    @NonNull
    CompletionStage<T> evaluate(@NonNull IO io);
}
