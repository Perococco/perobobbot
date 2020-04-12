package bot.common.lang;

import lombok.NonNull;

import java.util.ServiceLoader;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author perococco
 **/
public class FactoryProvider {

    public static <R,T> Factory<T,R> withLifeCycle(
            @NonNull Function<? super R, ? super T> constructor,
            @NonNull Consumer<? super R> initializer,
            @NonNull Consumer<? super R> finalizer
    ) {
        final WithLifeCycle withLifeCycle = ServiceLoaderHelper.getService(ServiceLoader.load(WithLifeCycle.class));
        return withLifeCycle.get(constructor,initializer,finalizer);
    }

    public static <R,T> Factory<T,R> basic(
            @NonNull Function<? super R, ? super T> constructor,
            @NonNull Consumer<? super R> initializer
    ) {
        final Basic basic = ServiceLoaderHelper.getService(ServiceLoader.load(Basic.class));
        return basic.get(constructor,initializer);
    }

    public interface WithLifeCycle extends Prioritized {

        @NonNull
        <R,T> Factory<T,R> get(
                @NonNull Function<? super R, ? super T> constructor,
                @NonNull Consumer<? super R> initializer,
                @NonNull Consumer<? super R> finalizer
                );

    }

    public interface Basic extends Prioritized {

        @NonNull
        <R,T> Factory<T,R> get(
                @NonNull Function<? super R, ? super T> constructor,
                @NonNull Consumer<? super R> initializer
                );

    }
}
