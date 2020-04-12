package bot.common.lang;

import lombok.NonNull;

import java.util.concurrent.CompletionStage;
import java.util.function.Function;

/**
 * @author perococco
 **/
public interface Identity<S> extends ReadOnlyIdentity<S> {

    @NonNull
    static <S> Identity<S> create(@NonNull S initialValue) {
        return IdentityFactory.getInstance().create(initialValue);
    }

    @NonNull
    CompletionStage<S> mutate(@NonNull Function<? super S, ? extends S> mutation);

    @NonNull
    Subscription addListener(@NonNull IdentityListener<S> listener);

}
