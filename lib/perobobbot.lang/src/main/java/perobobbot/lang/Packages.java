package perobobbot.lang;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;

import java.util.Arrays;
import java.util.stream.Stream;

public interface Packages {

    @NonNull String getName();

    /**
     * @return a stream of all the packages to includes in the spring configuration
     */
    @NonNull Stream<String> packageStream();

    @NonNull
    static Packages with(@NonNull String name, @NonNull Class<?>... markers) {
        return with(name, Arrays.stream(markers).map(Class::getPackageName).collect(ImmutableSet.toImmutableSet()));
    }

    @NonNull
    static Packages with(@NonNull String name, @NonNull ImmutableSet<String> packages) {
        return new Packages() {

            public @NonNull String getName() {
                return name;
            }

            @Override
            public @NonNull Stream<String> packageStream() {
                return packages.stream();
            }
        };
    }


}
