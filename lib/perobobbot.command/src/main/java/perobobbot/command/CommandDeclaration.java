package perobobbot.command;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.access.AccessRule;

@RequiredArgsConstructor
@Getter
public class CommandDeclaration {

    public static @NonNull CommandDeclaration.Factory factory(@NonNull String extensionName) {
        return (definition,access,action) -> new CommandDeclaration(extensionName,definition,access,action);
    }

    public interface Factory {

        /**
         *
         * @param definition the definition of the command used to create the parser (something like <code>play {x},{y} [arg3]</code>)
         * @param defaultAccessRule The default access rule if none is provided by the bot launching the command
         * @param commandAction The action associated with this definition
         * @return a command declaration made from the provided parameters
         */
        @NonNull CommandDeclaration create(@NonNull String definition, @NonNull AccessRule defaultAccessRule, @NonNull CommandAction commandAction);

        default @NonNull CommandDeclaration create(@NonNull String definition, @NonNull AccessRule defaultAccessRule, @NonNull Runnable runnable) {
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
