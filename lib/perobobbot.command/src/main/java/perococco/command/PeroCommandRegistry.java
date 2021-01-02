package perococco.command;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import perobobbot.access.AccessRule;
import perobobbot.command.Command;
import perobobbot.command.CommandAction;
import perobobbot.command.CommandParser;
import perobobbot.command.CommandRegistry;
import perobobbot.lang.MapTool;
import perobobbot.lang.Subscription;

import java.util.Optional;

public class PeroCommandRegistry implements CommandRegistry {

    private ImmutableMap<String, Value> commands = ImmutableMap.of();

    @Override
    @Synchronized
    public @NonNull Subscription addCommandDefinition(@NonNull String commandDefinition,
                                              @NonNull AccessRule defaultAccessRule,
                                              @NonNull CommandAction executor) {
        final var commandParser = CommandParser.create(commandDefinition);
        final var key = commandParser.getFullCommandName();
        if (commands.containsKey(key)) {
            throw new IllegalArgumentException("Duplicate command '" + commandDefinition + "'");
        }
        this.commands = MapTool.add(this.commands, key, new Value(commandParser, defaultAccessRule, executor));
        return () -> removeCommandDefinition(key);
    }

    @Synchronized
    private void removeCommandDefinition(@NonNull String key) {
        this.commands = MapTool.remove(this.commands,key);
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
