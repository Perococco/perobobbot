package perobobbot.command;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.access.AccessRule;

@RequiredArgsConstructor
@Getter
public class CommandDefinition {

    public static @NonNull CommandDefinition with(@NonNull String definition, @NonNull AccessRule defaultAccessRule, @NonNull CommandAction commandAction) {
        return new CommandDefinition(definition,defaultAccessRule,commandAction);
    }

    public static @NonNull CommandDefinition with(@NonNull String definition, @NonNull AccessRule defaultAccessRule, @NonNull Runnable runnable) {
        return new CommandDefinition(definition,defaultAccessRule,(p,c) -> runnable.run());
    }

    private final @NonNull String definition;

    private final @NonNull AccessRule defaultAccessRule;

    private final @NonNull CommandAction commandAction;
}
