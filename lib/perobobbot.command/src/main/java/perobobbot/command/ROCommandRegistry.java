package perobobbot.command;

import lombok.NonNull;

import java.util.Optional;

/**
 * A RO command registry, only used to find registered commands, use a {@link CommandRegistry} if you
 * want have to add commands
 */
public interface ROCommandRegistry {

    /**
     * Find a command from its name
     * @param message the message
     * @return an optional containing the command with the provided name, an empty optional otherwise.
     */
    @NonNull Optional<Command> findCommand(@NonNull String message);

}
