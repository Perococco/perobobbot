package bot.common.lang;

import lombok.NonNull;

import java.util.Comparator;
import java.util.ServiceLoader;

/**
 * @author perococco
 **/
public class ServiceLoaderHelper {

    @NonNull
    public static <S extends Prioritized> S getService(@NonNull ServiceLoader<S> serviceLoader) {
        return serviceLoader.stream()
                            .map(ServiceLoader.Provider::get)
                            .max(Comparator.comparingInt(Prioritized::priority))
                            .orElseThrow(() -> new RuntimeException("Could not find any implementation with "+serviceLoader));
    }
}
