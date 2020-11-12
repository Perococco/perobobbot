package perobobbot.common.lang;

import lombok.NonNull;

import java.util.ServiceLoader;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author perococco
 **/
@Priority(Integer.MIN_VALUE)
public class FactoryProvider {

    public static <R,T> Factory<T,R> withLifeCycle(
            @NonNull Function<? super R, ? super T> constructor,
            @NonNull Consumer<? super R> initializer,
            @NonNull Consumer<? super R> finalizer
    ) {
        return WithLifeCycleHolder.INSTANCE.get(constructor,initializer,finalizer);
    }

    public static <R,T> Factory<T,R> basic(
            @NonNull Function<? super R, ? super T> constructor,
            @NonNull Consumer<? super R> initializer
    ) {
        return BasicHolder.INSTANCE.get(constructor,initializer);
    }

    @Priority(Integer.MIN_VALUE)
    public interface WithLifeCycle {

        @NonNull
        <R,T> Factory<T,R> get(
                @NonNull Function<? super R, ? super T> constructor,
                @NonNull Consumer<? super R> initializer,
                @NonNull Consumer<? super R> finalizer
                );

    }

    @Priority(Integer.MIN_VALUE)
    public interface Basic {

        @NonNull
        <R,T> Factory<T,R> get(
                @NonNull Function<? super R, ? super T> constructor,
                @NonNull Consumer<? super R> initializer
                );

    }

    private static class WithLifeCycleHolder {
        private static final WithLifeCycle INSTANCE = ServiceLoaderHelper.getService(ServiceLoader.load(WithLifeCycle.class));
    }
    private static class BasicHolder {
        private static final Basic INSTANCE = ServiceLoaderHelper.getService(ServiceLoader.load(Basic.class));
    }
}
