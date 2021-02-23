package perobobbot.plugin;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;

import java.util.Arrays;
import java.util.stream.Stream;

public interface FunctionalPlugin extends Plugin {

    /**
     * @return a stream of all the packages to includes in the spring configuration
     */
    @NonNull Stream<String> packageStream();



    @NonNull
    static FunctionalPlugin with(@NonNull String context, @NonNull Class<?>... markers) {
        return with(context, Arrays.stream(markers).map(Class::getPackageName).collect(ImmutableSet.toImmutableSet()));
    }

    @NonNull
    static FunctionalPlugin with(@NonNull String context, @NonNull ImmutableSet<String> packages) {
        return new FunctionalPlugin() {

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
