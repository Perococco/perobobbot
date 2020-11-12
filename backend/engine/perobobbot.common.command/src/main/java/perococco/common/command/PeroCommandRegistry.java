package perococco.common.command;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.Synchronized;
import perobobbot.common.command.Command;
import perobobbot.common.command.CommandRegistry;
import perobobbot.common.lang.MapTool;
import perobobbot.common.lang.Subscription;

import java.util.Optional;

public class PeroCommandRegistry implements CommandRegistry {

    private ImmutableMap<String, Command> commands = ImmutableMap.of();

    @Override
    @Synchronized
    public @NonNull Subscription addCommand(@NonNull Command command) {
        if (commands.containsKey(command.name())) {
            throw new IllegalArgumentException("Duplicate command '" + command.name() + "'");
        }
        this.commands = MapTool.add(this.commands, command.name(), command);
        return () -> removeCommand(command.name());
    }

    @Override
    public @NonNull Optional<Command> findCommand(@NonNull String commandName) {
        return Optional.ofNullable(commands.get(commandName));
    }

    @Synchronized
    private void removeCommand(@NonNull String commandName) {
        this.commands = MapTool.remove(this.commands, commandName);
    }

}
