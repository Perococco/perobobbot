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
     * @param commandDeclaration the command definition
     */
    @NonNull Subscription addCommandDefinition(@NonNull CommandDeclaration commandDeclaration);

    default @NonNull Subscription addCommandDefinitions(@NonNull ImmutableCollection<CommandDeclaration> commandDeclarations) {
        return Subscription.multi(
                commandDeclarations.stream().map(this::addCommandDefinition).collect(ImmutableList.toImmutableList())
        );
    }

}
