package perococco.command;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import perobobbot.chat.core.IO;
import perobobbot.command.*;
import perobobbot.lang.*;
import perobobbot.lang.fp.Function0;
import perobobbot.lang.fp.Function1;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor
public class PeroCommandController implements CommandController {

    public static @NonNull CommandControllerBuilder builder(@NonNull IO io,
                                                            @NonNull Function1<? super CommandController,? extends Subscription> connector) {
        return new PeroCommandControllerBuilder(io,connector);
    }

    @Override
    public int priority() {
        return 100;
    }

    /**
     * IO used to send error message
     */
    private final @NonNull IO io;

    /**
     * used to convert exception that occurs when executing a command, to error message
     */
    private final @NonNull MessageErrorResolver messageErrorResolver;

    /**
     * The character that determine if a message is a command. This is generally le '!' char.
     * It can be different on different platform thus the map.
     */
    private final @NonNull ImmutableMap<Platform, Character> prefixes;

    /**
     * the command registry for each user.
     */
    private final @NonNull AtomicReference<ImmutableMap<Bot, ROCommandRegistry>> commandRegistryPerUser = new AtomicReference<>(ImmutableMap.of());

    /**
     * the function that must be call to connect this controller to the chat
     * the provided subscription must be unsubscribed when this controller is stopped
     */
    private final @NonNull Function1<? super CommandController,? extends Subscription> connector;

    private final SubscriptionHolder subscriptionHolder = new SubscriptionHolder();

    @Override
    public void handleMessage(@NonNull MessageContext messageContext) {
        final var prefix = getPrefix(messageContext.getPlatform());
        ExecutionContext.createFrom(prefix, messageContext)
                        .map(Executor::new)
                        .ifPresent(Executor::execute);
    }

    private char getPrefix(Platform platform) {
        final Character prefix = prefixes.get(platform);
        if (prefix == null) {
            throw new RuntimeException("No prefix defined for platform '" + platform + "'. This is a bug");
        }
        return prefix;
    }

    @Override
    public void start() {
        this.subscriptionHolder.replaceWith(() -> connector.f(this));
    }

    @Override
    public void stop() {
        this.subscriptionHolder.unsubscribe();
    }

    @Override
    public @NonNull Subscription addCommandRegistry(@NonNull Bot bot, @NonNull ROCommandRegistry commandRegistry) {
        this.commandRegistryPerUser.updateAndGet(m -> MapTool.add(m,bot,commandRegistry));
        return () -> removeCommandRegistry(bot);
    }

    private void removeCommandRegistry(@NonNull Bot bot) {
        this.commandRegistryPerUser.updateAndGet(m -> MapTool.remove(m,bot));
    }

    @RequiredArgsConstructor
    private class Executor {

        private final @NonNull ExecutionContext context;

        private ROCommandRegistry commandRegistry;

        private Command command;

        public void execute() {
            retrieveRegistryOfTheUser();
            if (couldNotFoundRegistry()) {
                return;
            }
            findCommandFromRegistry();
            if (commandHasBeenFound()) {
                executeCommand();
            } else {
                warnForInvalidCommand();
            }
        }

        private void retrieveRegistryOfTheUser() {
            this.commandRegistry = commandRegistryPerUser.get().get(context.getBot());

        }

        private boolean couldNotFoundRegistry() {
            return this.commandRegistry == null;
        }


        private void findCommandFromRegistry() {
            assert command == null;
            this.command = commandRegistry.findCommand(context.getCommandName()).orElse(null);
        }

        private boolean commandHasBeenFound() {
            return command != null;
        }

        private void executeCommand() {
            try {
                command.execute(context);
            } catch (PerobobbotException e) {

            }
        }

        private void warnForInvalidCommand() {
            //do nothing yet
        }
    }
}
