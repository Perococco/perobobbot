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

    /**
     * The name of the extension the command belongs to
     */
    private final @NonNull String extensionName;

    /**
     * The definition of the command used to create the parser (something like <code>play {x},{y} [arg3]</code>)
     */
    private final @NonNull String definition;

    /**
     * The default access rule if none is provided by the bot launching the command
     */
    private final @NonNull AccessRule defaultAccessRule;

    /**
     * The action associated with this definition
     */
    private final @NonNull CommandAction commandAction;
}
