package perobobbot.lang;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;

import java.util.Comparator;
import java.util.ServiceLoader;

/**
 * @author perococco
 **/
public class ServiceLoaderHelper {

    @NonNull
    public static <S> S getService(@NonNull ServiceLoader<S> serviceLoader) {
        return serviceLoader.stream()
                            .max(Comparator.comparingInt(ServiceLoaderHelper::getPriority))
                            .map(ServiceLoader.Provider::get)
                            .orElseThrow(() -> new RuntimeException("Could not find any implementation with "+serviceLoader));
    }

    @NonNull
    public static <S> ImmutableList<S> getServices(@NonNull ServiceLoader<S> serviceLoader) {
        return serviceLoader.stream()
                            .sorted(Comparator.comparingInt(ServiceLoaderHelper::getPriority))
                            .map(ServiceLoader.Provider::get)
                            .collect(ImmutableList.toImmutableList());
    }

    private static int getPriority(@NonNull ServiceLoader.Provider<?> provider) {
        final Priority priority = provider.type().getAnnotation(Priority.class);
        return priority == null ? Integer.MIN_VALUE:priority.value();
    }
}
