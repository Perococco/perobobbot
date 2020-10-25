package perococco.perobobbot.chat.core;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import perobobbot.chat.core.ChatController;
import perobobbot.chat.core.ChatControllerBuilder;
import perobobbot.common.lang.Platform;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PerococcoChatControllerBuilder implements ChatControllerBuilder {

    private char defaultPrefix = '!';

    private final Map<Platform, Character> prefixes = new HashMap<>();

    @Override
    public @NonNull ChatControllerBuilder setCommandPrefix(char prefix) {
        this.defaultPrefix = prefix;
        return this;
    }

    @Override
    public @NonNull ChatControllerBuilder setCommandPrefix(@NonNull Platform platform, char prefix) {
        prefixes.put(platform, prefix);
        return this;
    }

    @Override
    public @NonNull ChatController build() {
        final ImmutableMap<Platform, Character> prefixes = Arrays.stream(Platform.values())
                                                                 .collect(ImmutableMap.toImmutableMap(p -> p, this::getPrefix));
        return new PerococcoChatController(prefixes);
    }

    private char getPrefix(@NonNull Platform platform) {
        return prefixes.getOrDefault(platform, defaultPrefix);
    }
}
