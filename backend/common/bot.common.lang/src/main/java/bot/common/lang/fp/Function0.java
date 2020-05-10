package bot.common.lang.fp;

import lombok.NonNull;

import java.util.function.Supplier;

public interface Function0<R> extends Supplier<R> {

    @NonNull
    static <A,R> Function0<R> toFunction0(@NonNull Supplier<R> function) {
        if (function instanceof Function0) {
            return (Function0<R>)function;
        }
        return function::get;
    }

    @NonNull
    R f();

    @Override
    @NonNull
    default R get() {
        return f();
    }

    @NonNull
    default <S> Function0<S> then(@NonNull Function1<? super R, ? extends S> after) {
        return () -> after.f(this.f());
    }
}
