package perobobbot.command;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.lang.Subscription;
import perococco.command.PeroCommandRegistry;

public interface CommandRegistry extends ROCommandRegistry {

    static @NonNull CommandRegistry create() {
        return new PeroCommandRegistry();
    }

    /**
     * Add a command to the manager
     * @param commandDefinition the command definition
     */
    @NonNull Subscription addCommandDefinition(@NonNull CommandDefinition commandDefinition);

    default @NonNull Subscription addCommandDefinitions(@NonNull ImmutableCollection<CommandDefinition> commandDefinitions) {
        return Subscription.multi(
                commandDefinitions.stream().map(this::addCommandDefinition).collect(ImmutableList.toImmutableList())
        );
    }

}
