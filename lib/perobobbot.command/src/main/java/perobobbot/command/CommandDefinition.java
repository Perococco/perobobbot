package perobobbot.command;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.access.AccessRule;

@RequiredArgsConstructor
@Getter
public class CommandDefinition {

    public static @NonNull CommandDefinition.Factory factory(@NonNull String extensionName) {
        return (definition,access,action) -> new CommandDefinition(extensionName,definition,access,action);
    }

    public interface Factory {

        @NonNull CommandDefinition create(@NonNull String definition, @NonNull AccessRule defaultAccessRule, @NonNull CommandAction commandAction);

        default @NonNull CommandDefinition create(@NonNull String definition, @NonNull AccessRule defaultAccessRule, @NonNull Runnable runnable) {
            return create(definition,defaultAccessRule,(p,c) -> runnable.run());
        }

    }

    private final @NonNull String extensionName;

    private final @NonNull String definition;

    private final @NonNull AccessRule defaultAccessRule;

    private final @NonNull CommandAction commandAction;
}
