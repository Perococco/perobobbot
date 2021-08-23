package perococco.command;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import perobobbot.command.*;
import perobobbot.lang.*;

import java.util.AbstractCollection;
import java.util.Comparator;
import java.util.Optional;
import java.util.UUID;

@Log4j2
public class PeroCommandRegistry implements CommandRegistry {

    private static final Comparator<Value> LONGEST_COMMAND_NAME_FIRST = Comparator.comparing(Value::getFullCommandName).reversed();


    private ImmutableList<Value> commands = ImmutableList.of();

    private ImmutableMap<String, ImmutableSet<Value>> valuesByCommandName = ImmutableMap.of();

    @Override
    public @NonNull Subscription addCommandDefinition(@NonNull CommandDeclaration commandDeclaration) {
        try {
            return this.doAddCommandDefinition(commandDeclaration);
        } catch (Throwable t) {
            ThrowableTool.interruptThreadIfCausedByInterruption(t);
            LOG.error("Error while adding command definition : {}", commandDeclaration.getDefinition(), t);
            return Subscription.NONE;
        }
    }


    @Override
    public @NonNull ImmutableSet<CommandDeclaration> getAllCommands() {
        return commands.stream()
                       .map(Value::getCommandDeclaration)
                       .collect(ImmutableSet.toImmutableSet());
    }

    private void checkForConflict(@NonNull Value value) {
        final Value conflicting = valuesByCommandName.getOrDefault(value.getFullCommandName(), ImmutableSet.of())
                                                     .stream().filter(value::isInConflictWith)
                                                     .findFirst()
                                                     .orElse(null);
        if (conflicting == null) {
            return;
        }
        throw new IllegalArgumentException("Definition '" + value.getCommandDeclaration() + "' is in conflict with '" + conflicting.getCommandDeclaration() + "'");
    }

    @Synchronized
    private @NonNull Subscription doAddCommandDefinition(@NonNull CommandDeclaration commandDeclaration) {
        final var value = Value.from(commandDeclaration);
        this.checkForConflict(value);
        this.commands = ListTool.addInOrderedList(this.commands, value, LONGEST_COMMAND_NAME_FIRST);
        this.valuesByCommandName = MapTool.update(this.valuesByCommandName, value.getFullCommandName(), () -> ImmutableSet.of(value), values -> SetTool.add(values, value));
        return () -> removeCommandDefinition(value);
    }

    @Synchronized
    private void removeCommandDefinition(@NonNull Value value) {
        this.commands = ListTool.removeFirst(this.commands, value);
        this.valuesByCommandName = MapTool.update(this.valuesByCommandName,
                value.getFullCommandName(),
                ImmutableSet::of,
                SetTool.remover(value),
                AbstractCollection::isEmpty);
    }


    @Override
    public @NonNull Optional<Command> findCommand(@NonNull String message) {
        return commands.stream()
                       .map(v -> v.parse(message))
                       .flatMap(Optional::stream)
                       .findFirst();
    }

    @RequiredArgsConstructor
    @EqualsAndHashCode(of = "id")
    private static class Value {

        public static @NonNull Value from(@NonNull CommandDeclaration commandDeclaration) {
            return new Value(commandDeclaration,CommandParser.create(commandDeclaration.getDefinition()));
        }

        private final UUID id = UUID.randomUUID();

        @Getter
        private final @NonNull CommandDeclaration commandDeclaration;
        private final @NonNull CommandParser commandParser;

        public @NonNull Optional<Command> parse(@NonNull String message) {
            return commandParser.parse(message).map(this::createCommand);
        }

        private @NonNull Command createCommand(@NonNull CommandParsing commandParsing) {
            return new Command(
                    commandDeclaration.getExtensionName(),
                    commandParsing,
                    commandDeclaration.getDefaultAccessRule(),
                    commandDeclaration.getCommandAction());
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
