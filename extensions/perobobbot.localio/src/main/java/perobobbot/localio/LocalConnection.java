package perobobbot.localio;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import perobobbot.chat.core.ChatConnection;
import perobobbot.chat.core.MessageChannelIO;
import perobobbot.lang.*;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
public class LocalConnection implements ChatConnection {

    @Getter
    private final ChatConnectionInfo chatConnectionInfo;
    private final @NonNull LocalSender localSender;

    private LocalIO local = null;

    @Override
    public @NonNull CompletionStage<? extends MessageChannelIO> join(@NonNull String channelName) {
        return CompletableFuture.completedFuture(getLocalIO());
    }

    @Override
    public @NonNull CompletionStage<? extends Optional<? extends MessageChannelIO>> findChannel(@NonNull String channelName) {
        return CompletableFuture.completedFuture(Optional.of(getLocalIO()));
    }

    @Synchronized
    private @NonNull LocalIO getLocalIO() {
        if (local == null) {
            local = new LocalIO(chatConnectionInfo,localSender);
        }
        return local;
    }

    @Synchronized
    public void dispose() {
        if (local != null) {
            local = null;
        }
    }
}
