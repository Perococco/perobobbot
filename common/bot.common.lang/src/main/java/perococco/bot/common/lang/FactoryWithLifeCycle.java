package perococco.bot.common.lang;

import bot.common.lang.Disposer;
import bot.common.lang.Factory;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author perococco
 **/
@RequiredArgsConstructor
public class FactoryWithLifeCycle<T,R> implements Factory<T,R> {

    @NonNull
    private final Function<? super R, ? extends T> constructor;

    @NonNull
    private final Consumer<? super T> initializer;

    @NonNull
    private final Consumer<? super T> finalizer;

    @NonNull
    @Override
    public T create(@NonNull R parameter) {
        final T item = constructor.apply(parameter);
        initializer.accept(item);
        return Holder.DISPOSER.add(item,finalizer);
    }

    private static class Holder {

        private static final Disposer DISPOSER = new Disposer();
    }
}
