package perobobbot.command;

import lombok.NonNull;
import perobobbot.access.AccessRule;
import perococco.command.PeroCommandRegistry;

public interface CommandRegistry extends ROCommandRegistry {

    static @NonNull CommandRegistry create() {
        return new PeroCommandRegistry();
    }

    /**
     * Add a command to the manager
     * @param commandDefinition the command definition
     * @param executor the executor launch when the parsing is successful
     */
    @NonNull void addCommandDefinition(@NonNull String commandDefinition, @NonNull AccessRule defaultAccessRule, @NonNull CommandAction executor);

    @NonNull void addCommandDefinition(@NonNull CommandDefinition commandDefinition);

}
