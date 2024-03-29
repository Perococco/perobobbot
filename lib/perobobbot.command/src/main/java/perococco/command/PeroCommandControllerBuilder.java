package perococco.command;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.command.CommandController;
import perobobbot.command.CommandControllerBuilder;
import perobobbot.command.CommandExecutor;
import perobobbot.command.CommandRegistry;
import perobobbot.lang.MessageDispatcher;
import perobobbot.lang.Platform;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class PeroCommandControllerBuilder implements CommandControllerBuilder {

    private char defaultPrefix = '!';

    private final Map<Platform, Character> prefixes = new HashMap<>();

    private final @NonNull MessageDispatcher messageDispatcher;
    private final @NonNull CommandExecutor commandExecutor;

    private CommandRegistry commandRegistry;

    @Override
    public @NonNull CommandControllerBuilder commandRegistry(@NonNull CommandRegistry commandRegistry) {
        this.commandRegistry = commandRegistry;
        return this;
    }

    @Override
    public @NonNull CommandControllerBuilder setCommandPrefix(char prefix) {
        this.defaultPrefix = prefix;
        return this;
    }

    @Override
    public @NonNull CommandControllerBuilder setCommandPrefix(@NonNull Platform platform, char prefix) {
        prefixes.put(platform, prefix);
        return this;
    }

    @Override
    public @NonNull CommandController build() {
        final ImmutableMap<Platform, Character> prefixes = Arrays.stream(Platform.values())
                                                                 .collect(ImmutableMap.toImmutableMap(p -> p, this::getPrefix));
        return new PeroCommandController(commandRegistry,commandExecutor,prefixes,messageDispatcher);
    }

    private char getPrefix(@NonNull Platform platform) {
        return prefixes.getOrDefault(platform, defaultPrefix);
    }
}
