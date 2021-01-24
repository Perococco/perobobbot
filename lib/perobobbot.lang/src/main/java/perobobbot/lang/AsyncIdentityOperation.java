package perobobbot.lang;

import lombok.NonNull;

import java.util.concurrent.CompletionStage;

public interface AsyncIdentityOperation<S> {

    @NonNull
    <T> CompletionStage<T> operate(@NonNull Operator<S,T> operator);

    @NonNull
    default CompletionStage<S> mutate(@NonNull Mutation<S> mutation) {
        return operate(mutation.asOperator());
    }

}
