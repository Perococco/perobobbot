package perococco.command;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import perobobbot.command.Command;
import perobobbot.command.CommandController;
import perobobbot.command.CommandExecutor;
import perobobbot.command.CommandRegistry;
import perobobbot.lang.*;

@RequiredArgsConstructor
@Log4j2
public class PeroCommandController implements CommandController {

    @Override
    public int priority() {
        return DEFAULT_PRIORITY+100;
    }


    private final @NonNull CommandRegistry commandRegistry;

    private final @NonNull CommandExecutor commandExecutor;


    /**
     * The character that determine if a message is a command. This is generally le '!' char.
     * It can be different on different platform thus the map.
     */
    private final @NonNull ImmutableMap<Platform, Character> prefixes;

    /**
     * the function that must be call to connect this controller to the chat
     * the provided subscription must be unsubscribed when this controller is stopped
     */
    private final @NonNull MessageDispatcher messageDispatcher;

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
        this.subscriptionHolder.replaceWith(() -> messageDispatcher.addListener(this));
    }

    @Override
    public void stop() {
        this.subscriptionHolder.unsubscribe();
    }

    @RequiredArgsConstructor
    private class Executor {

        private final @NonNull ExecutionContext context;

        private Command command;

        public void execute() {
            findCommandFromRegistry();
            if (commandHasBeenFound()) {
                executeCommand();
            } else {
                warnForInvalidCommand();
            }
        }

        private void findCommandFromRegistry() {
            assert command == null;
            this.command = commandRegistry.findCommand(context.getCommand()).orElse(null);
        }

        private boolean commandHasBeenFound() {
            return command != null;
        }

        private void executeCommand() {
            commandExecutor.execute(command, context);
        }

        private void warnForInvalidCommand() {
            //do nothing yet
        }
    }
}
