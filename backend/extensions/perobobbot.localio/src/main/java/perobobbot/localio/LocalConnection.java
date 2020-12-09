package perobobbot.localio;

import lombok.Getter;
import lombok.NonNull;
import lombok.Synchronized;
import perobobbot.chat.core.ChatConnection;
import perobobbot.chat.core.MessageChannelIO;
import perobobbot.command.CommandBundleLifeCycle;
import perobobbot.lang.*;
import perobobbot.lang.fp.Function1;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class LocalConnection implements ChatConnection {

    @Getter
    private final @NonNull String nick;

    private final @NonNull ApplicationCloser applicationCloser;

    private final @NonNull Listeners<MessageListener> listeners;

    private final @NonNull Function1<LocalIO, CommandBundleLifeCycle> lifeCycleFactory;

    private LocalIO local = null;

    public LocalConnection(@NonNull String nick,
                           @NonNull ApplicationCloser applicationCloser,
                           @NonNull Function1<LocalIO, CommandBundleLifeCycle> lifeCycleFactory,
                           @NonNull Listeners<MessageListener> listeners) {
        this.applicationCloser = applicationCloser;
        this.nick = nick;
        this.lifeCycleFactory = lifeCycleFactory;
        this.listeners = listeners;
    }

    @Override
    public @NonNull Platform getPlatform() {
        return Platform.LOCAL;
    }


    @Override
    public @NonNull CompletionStage<? extends MessageChannelIO> join(@NonNull String channelName) {
        return CompletableFuture.completedFuture(getLocalIO());
    }

    @Override
    public @NonNull CompletionStage<? extends Optional<? extends MessageChannelIO>> findChannel(@NonNull String channelName) {
        return CompletableFuture.completedFuture(Optional.of(getLocalIO()));
    }

    public @NonNull Subscription addMessageListener(@NonNull MessageListener listener) {
        return listeners.addListener(listener);
    }

    @Synchronized
    private @NonNull LocalIO getLocalIO() {
        if (local == null) {
            local = new LocalIO(applicationCloser, lifeCycleFactory,listeners);
            local.enable();
        }
        return local;
    }

    @Synchronized
    public void disconnectAll() {
        if (local != null) {
            local.disable();
            local = null;
        }
    }
}
