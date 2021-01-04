package perobobbot.lang;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Stream;

/**
 * @author Perococco
 */
public interface Plugin {

    Comparator<Plugin> TYPE_THEN_NAME = Comparator.comparing(Plugin::type).thenComparing(Plugin::name);

    @NonNull PluginType type();

    @NonNull String name();

    @NonNull Stream<String> packageStream();

    @NonNull
    static Plugin with(@NonNull PluginType type, @NonNull String context, @NonNull Class<?>... markers) {
        return with(type, context, Arrays.stream(markers).map(Class::getPackageName).collect(ImmutableSet.toImmutableSet()));
    }

    @NonNull
    static Plugin with(@NonNull PluginType type, @NonNull String context, @NonNull ImmutableSet<String> packages) {
        return new Plugin() {
            @Override
            public @NonNull PluginType type() {
                return type;
            }

            @Override
            public @NonNull String name() {
                return context;
            }

            @Override
            public @NonNull Stream<String> packageStream() {
                return packages.stream();
            }
        };
    }

}
