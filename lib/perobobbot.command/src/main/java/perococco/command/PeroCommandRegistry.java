package perococco.command;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.access.AccessRule;
import perobobbot.command.*;
import perobobbot.lang.MapTool;

import java.util.Optional;

public class PeroCommandRegistry implements CommandRegistry {

    private ImmutableMap<String, Value> commands = ImmutableMap.of();


    @Override
    public @NonNull void addCommandDefinition(@NonNull String commandDefinition,
                                              @NonNull AccessRule defaultAccessRule,
                                              @NonNull CommandAction executor) {
        final var commandParser = CommandParser.create(commandDefinition);
        final var key = commandParser.getFullCommandName();
        if (commands.containsKey(key)) {
            throw new IllegalArgumentException("Duplicate command '" + commandDefinition + "'");
        }
        this.commands = MapTool.add(this.commands, key, new Value(commandParser, defaultAccessRule, executor));
    }

    @Override
    public @NonNull void addCommandDefinition(@NonNull CommandDefinition commandDefinition) {
        this.addCommandDefinition(commandDefinition.getDefinition(),commandDefinition.getDefaultAccessRule(),commandDefinition.getCommandAction());
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
        private final @NonNull CommandParser commandParser;
        private final @NonNull AccessRule defaultAccessRule;
        private final @NonNull CommandAction commandAction;

        public @NonNull Optional<Command> parse(@NonNull String message) {
            return commandParser.parse(message)
                                .map(c -> new Command(c, defaultAccessRule, commandAction));
        }
    }

}
