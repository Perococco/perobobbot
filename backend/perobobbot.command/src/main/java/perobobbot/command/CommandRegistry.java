package perobobbot.command;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.lang.Subscription;
import perococco.command.PeroCommandRegistry;

import java.util.Optional;

/**
 * A registered command (with one of the methods {@link #addCommand(Command)}, {@link #addCommands(Command...)}
 * or {@link #addCommands(ImmutableCollection)} can be found in the registery from its chat name form
 */
public interface CommandRegistry {

    static @NonNull CommandRegistry create() {
        return new PeroCommandRegistry();
    }

    /**
     * Add a command to the manager
     * @param command the command to add
     * @return a subscription to remove the provided command from the manager
     */
    @NonNull Subscription addCommand(@NonNull Command command);

    /**
     * Find a command from its name
     * @param commandName the command name
     * @return an optional containing the command with the provided name, an empty optional otherwise.
     */
    @NonNull Optional<Command> findCommand(@NonNull String commandName);

    /**
     * Add several commands to the manager
     * @param commands the commands to add
     * @return a subscription to remove the provided commands from the manager
     */
    default @NonNull Subscription addCommands(@NonNull Command... commands) {
        return addCommands(ImmutableList.copyOf(commands));
    }

    /**
     * Add several commands to the manager
     * @param commands the commands to add
     * @return a subscription to remove the provided commands from the manager
     */
    default @NonNull Subscription addCommands(@NonNull ImmutableCollection<Command> commands) {
        return commands.stream().map(this::addCommand).collect(Subscription.COLLECTOR);
    }

}
