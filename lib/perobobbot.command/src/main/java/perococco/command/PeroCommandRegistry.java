package perococco.command;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.Value;
import lombok.extern.log4j.Log4j2;
import perobobbot.access.AccessRule;
import perobobbot.command.*;
import perobobbot.lang.MapTool;
import perobobbot.lang.Subscription;
import perobobbot.lang.ThrowableTool;

import java.util.Optional;

@Log4j2
public class PeroCommandRegistry implements CommandRegistry {

    private ImmutableMap<String, Value> commands = ImmutableMap.of();

    @Override
    public @NonNull Subscription addCommandDefinition(@NonNull CommandDefinition commandDefinition) {
        try {
            return this.doAddCommandDefinition(commandDefinition);
        } catch (Throwable t) {
            ThrowableTool.interruptThreadIfCausedByInterruption(t);
            LOG.error("Error while adding command definition : {}", commandDefinition.getDefinition(), t);
            return Subscription.NONE;
        }
    }

    @Synchronized
    private @NonNull Subscription doAddCommandDefinition(@NonNull CommandDefinition commandDefinition) {
        final var value = Value.from(commandDefinition);
        final var key = value.getFullCommandName();
        if (commands.containsKey(key)) {
            throw new IllegalArgumentException("Duplicate command '" + key + "'");
        }
        this.commands = MapTool.add(this.commands, key, value);
        return () -> removeCommandDefinition(key);
    }

    @Synchronized
    private void removeCommandDefinition(@NonNull String key) {
        this.commands = MapTool.remove(this.commands, key);
    }


    @Override
    public @NonNull Optional<Command> findCommand(@NonNull String message) {
        return commands.values()
                       .stream()
                       .map(v -> v.parse(message))
                       .flatMap(Optional::stream)
                       .findFirst();
    }

    @RequiredArgsConstructor
    private static class Value {

        public static @NonNull Value from(@NonNull CommandDefinition commandDefinition) {
            return new Value(commandDefinition.getExtensionName(),
                             CommandParser.create(commandDefinition.getDefinition()),
                             commandDefinition.getDefaultAccessRule(),
                             commandDefinition.getCommandAction()
            );
        }


        private final @NonNull String extensionName;
        private final @NonNull CommandParser commandParser;
        private final @NonNull AccessRule defaultAccessRule;
        private final @NonNull CommandAction commandAction;

        public @NonNull Optional<Command> parse(@NonNull String message) {
            return commandParser.parse(message)
                                .map(c -> new Command(extensionName, c, defaultAccessRule, commandAction));
        }

        public @NonNull String getFullCommandName() {
            return commandParser.getFullCommandName();
        }
    }

}
