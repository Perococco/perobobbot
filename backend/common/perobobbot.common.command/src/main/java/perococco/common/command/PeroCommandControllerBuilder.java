package perococco.common.command;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import perobobbot.common.command.CommandController;
import perobobbot.common.command.CommandControllerBuilder;
import perobobbot.common.command.CommandRegistry;
import perobobbot.common.lang.Platform;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PeroCommandControllerBuilder implements CommandControllerBuilder {

    private char defaultPrefix = '!';

    private final Map<Platform, Character> prefixes = new HashMap<>();

    private CommandRegistry commandRegistry = null;

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
    public @NonNull CommandControllerBuilder setCommandRegistry(@NonNull CommandRegistry commandRegistry) {
        this.commandRegistry = commandRegistry;
        return this;
    }

    @Override
    public @NonNull CommandController build() {
        final ImmutableMap<Platform, Character> prefixes = Arrays.stream(Platform.values())
                                                                 .collect(ImmutableMap.toImmutableMap(p -> p, this::getPrefix));
        return new PeroCommandController(prefixes,commandRegistry);
    }

    private char getPrefix(@NonNull Platform platform) {
        return prefixes.getOrDefault(platform, defaultPrefix);
    }
}
