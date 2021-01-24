package perobobbot.lang;

import lombok.NonNull;
import perobobbot.lang.fp.Consumer1;
import perobobbot.lang.fp.Function1;

import java.util.concurrent.CompletionStage;

/**
 * @author perococco
 **/
public interface AsyncIdentity<S> extends ReadOnlyAsyncIdentity<S>, AsyncIdentityOperation<S> {

    @NonNull
    static <S> AsyncIdentity<S> create(@NonNull S initialValue) {
        return AsyncIdentityFactory.getInstance().create(initialValue);
    }

    @Override
    @NonNull
    default CompletionStage<S> getRootState() {
        return operate(Operator.getter(Function1.identity()));
    }

    @Override
    @NonNull
    default <T> CompletionStage<T> applyToState(@NonNull Function1<? super S, ? extends T> action) {
        return operate(Operator.getter(action));
    }

    @Override
    @NonNull
    default CompletionStage<?> runWithState(@NonNull Consumer1<? super S> action) {
        return operate(Operator.getter(s -> {action.accept(s);return Nil.NIL;}));
    }
}
