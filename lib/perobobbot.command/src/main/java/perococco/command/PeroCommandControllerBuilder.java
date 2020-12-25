package perococco.command;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.chat.core.IO;
import perobobbot.command.CommandController;
import perobobbot.command.CommandControllerBuilder;
import perobobbot.command.CommandRegistry;
import perobobbot.command.MessageErrorResolver;
import perobobbot.lang.Platform;
import perobobbot.lang.Subscription;
import perobobbot.lang.Todo;
import perobobbot.lang.fp.Function1;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class PeroCommandControllerBuilder implements CommandControllerBuilder {

    private char defaultPrefix = '!';

    private final Map<Platform, Character> prefixes = new HashMap<>();

    private final IO io;
    private final Function1<? super CommandController,? extends Subscription> connector;

    private MessageErrorResolver messageErrorResolver = new BasicMessageErrorResolver();

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
    public @NonNull CommandControllerBuilder setErrorMessageResolver(@NonNull MessageErrorResolver messageResolver) {
        this.messageErrorResolver = messageResolver;
        return this;
    }

    @Override
    public @NonNull CommandController build() {
        final ImmutableMap<Platform, Character> prefixes = Arrays.stream(Platform.values())
                                                                 .collect(ImmutableMap.toImmutableMap(p -> p, this::getPrefix));
        return new PeroCommandController(io,messageErrorResolver,prefixes,connector);
    }

    private char getPrefix(@NonNull Platform platform) {
        return prefixes.getOrDefault(platform, defaultPrefix);
    }
}
