package perobobbot.common.lang;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author Perococco
 */
public interface Packages {

    @NonNull
    String context();

    @NonNull
    Stream<String> stream();

    @NonNull
    static Packages with(@NonNull String context, @NonNull Class<?>... markers) {
        return with(context, Arrays.stream(markers).map(Class::getPackageName).collect(ImmutableSet.toImmutableSet()));
    }

    @NonNull
    static Packages with(@NonNull String context, @NonNull ImmutableSet<String> packages) {
        return new Packages() {
            @Override
            public @NonNull String context() {
                return context;
            }

            @Override
            public @NonNull Stream<String> stream() {
                return packages.stream();
            }
        };
    }

}
