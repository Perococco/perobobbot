package perococco.command;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.command.Command;
import perobobbot.command.CommandController;
import perobobbot.command.CommandControllerBuilder;
import perobobbot.command.CommandRegistry;
import perobobbot.lang.ExecutionContext;
import perobobbot.lang.MessageContext;
import perobobbot.lang.Platform;

import java.util.Optional;

@RequiredArgsConstructor
public class PeroCommandController implements CommandController {

    public static @NonNull CommandControllerBuilder builder() {
        return new PeroCommandControllerBuilder();
    }

    @Override
    public int priority() {
        return 100;
    }

    private @NonNull final ImmutableMap<Platform, Character> prefixes;

    private final @NonNull CommandRegistry commandRegistry;

    @Override
    public boolean handleMessage(@NonNull MessageContext messageContext) {
        final var prefix = getPrefix(messageContext.getPlatform());
        final var context = ExecutionContext.from(prefix,messageContext).orElse(null);

        if (context == null) {
            return false;
        } else {
            return executeCommand(context);
        }

    }

    private boolean executeCommand(@NonNull ExecutionContext executionContext) {
        final Optional<Command> command = commandRegistry.findCommand(executionContext.getCommandName());
        command.ifPresent(c -> c.execute(executionContext));
        return command.isPresent();
    }

    private char getPrefix(Platform platform) {
        final Character prefix = prefixes.get(platform);
        if (prefix == null) {
            throw new RuntimeException("No prefix defined for platform '" + platform + "'. This is a bug");
        }
        return prefix;
    }

}
