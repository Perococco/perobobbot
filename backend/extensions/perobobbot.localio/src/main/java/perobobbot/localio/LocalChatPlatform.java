package perobobbot.localio;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import perobobbot.access.AccessRule;
import perobobbot.access.Policy;
import perobobbot.access.PolicyManager;
import perobobbot.chat.core.ChatAuthentication;
import perobobbot.chat.core.ChatConnection;
import perobobbot.chat.core.ChatPlatform;
import perobobbot.command.CommandBundle;
import perobobbot.command.CommandBundleLifeCycle;
import perobobbot.command.CommandRegistry;
import perobobbot.lang.*;
import perobobbot.lang.fp.Function1;
import perobobbot.localio.spring.ShowGui;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class LocalChatPlatform implements ChatPlatform {


    private final @NonNull ApplicationCloser applicationCloser;

    private final @NonNull Listeners<MessageListener> listeners = new Listeners<>();

    private final @NonNull CommandRegistry commandRegistry;

    private final @NonNull PolicyManager policyManager;

    final Function1<LocalIO, CommandBundleLifeCycle> cycleFunction;


    private final Map<String, LocalConnection> localConnections = new HashMap<>();

    public LocalChatPlatform(@NonNull ApplicationCloser applicationCloser, @NonNull CommandRegistry commandRegistry, @NonNull PolicyManager policyManager) {
        this.applicationCloser = applicationCloser;
        this.commandRegistry = commandRegistry;
        this.policyManager = policyManager;
        final Policy policy = policyManager.createPolicy(AccessRule.create(Role.THE_BOSS, Duration.ofSeconds(0)));

        this.cycleFunction = local -> CommandBundle.builder()
                                                   .add("lio show-gui", policy, new ShowGui(local))
                                                   .add("lio hide-gui", policy, local::hideGui)
                                                   .build()
                                                   .createLifeCycle(commandRegistry);
    }

    @Override
    public @NonNull Platform getPlatform() {
        return Platform.LOCAL;
    }

    @Override
    public @NonNull CompletionStage<? extends ChatConnection> connect(@NonNull ChatAuthentication authentication) {
        return CompletableFuture.completedFuture(getLocalConnection(authentication.getNick()));
    }

    @Override
    public @NonNull Optional<CompletionStage<? extends ChatConnection>> findConnection(@NonNull String nick) {
        return Optional.of(CompletableFuture.completedFuture(getLocalConnection(nick)));
    }

    @Synchronized
    private @NonNull LocalConnection getLocalConnection(@NonNull String nick) {
        return localConnections.computeIfAbsent(nick, this::createLocalConnectionForUser);
    }

    private @NonNull LocalConnection createLocalConnectionForUser(@NonNull String nick) {
        return new LocalConnection(nick, applicationCloser, cycleFunction, listeners);
    }

    @Override
    public @NonNull Subscription addMessageListener(@NonNull MessageListener listener) {
        return listeners.addListener(listener);
    }

    @Override
    @Synchronized
    public void disconnectAll() {
        localConnections.values().forEach(LocalConnection::disconnectAll);
        localConnections.clear();
    }
}
