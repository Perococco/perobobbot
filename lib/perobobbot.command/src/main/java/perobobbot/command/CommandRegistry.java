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
 *
 * WARNING the commandRegistry does not known anything about the user. This might be problematic
 * when multiple users will be managed by the bot.
 */
public interface CommandRegistry extends ROCommandRegistry {

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
