package perococco.command;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.extern.log4j.Log4j2;
import perobobbot.access.AccessRule;
import perobobbot.command.*;
import perobobbot.lang.MapTool;
import perobobbot.lang.SetTool;
import perobobbot.lang.Subscription;
import perobobbot.lang.ThrowableTool;

import java.util.AbstractCollection;
import java.util.Optional;
import java.util.UUID;

@Log4j2
public class PeroCommandRegistry implements CommandRegistry {

    private ImmutableMap<UUID, Value> commands = ImmutableMap.of();

    private ImmutableMap<String, ImmutableSet<Value>> valuesByCommandName = ImmutableMap.of();

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
        this.checkForConflict(value);
        final var key = UUID.randomUUID();
        this.commands = MapTool.add(this.commands, key, value);
        this.valuesByCommandName = MapTool.update(this.valuesByCommandName, value.getFullCommandName(), () -> ImmutableSet.of(value), values -> SetTool.add(values, value));
        return () -> removeCommandDefinition(key);
    }

    private void checkForConflict(@NonNull Value value) {
        final Value conflicting = valuesByCommandName.getOrDefault(value.getFullCommandName(), ImmutableSet.of())
                                           .stream().filter(value::isInConflictWith)
                                           .findFirst()
                                           .orElse(null);
        if (conflicting == null) {
            return;
        }
        throw new IllegalArgumentException("Definition '"+value.getCommandDefinition()+"' is in conflict with '"+conflicting.getCommandDefinition()+"'");
    }

    @Synchronized
    private void removeCommandDefinition(@NonNull UUID key) {
        final var value = this.commands.get(key);
        if (value == null) {
            return;
        }
        this.commands = MapTool.remove(this.commands, key);
        this.valuesByCommandName = MapTool.update(this.valuesByCommandName,
                                                  value.getFullCommandName(),
                                                  ImmutableSet::of,
                                                  SetTool.remover(value),
                                                  AbstractCollection::isEmpty);
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

        public @NonNull String getCommandDefinition() {
            return commandParser.getCommandDefinition();
        }

        public boolean isInConflictWith(@NonNull Value other) {
            return commandParser.isInConflictWith(other.commandParser);
        }
    }

}
