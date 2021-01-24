package perobobbot.action;

import lombok.NonNull;
import perococco.perobobbot.action.Head;

import java.util.Optional;

public interface ActionChain<P,R> extends Launchable<P,R> {

    @NonNull
    static <P,R> ActionChain<P,R> head(@NonNull Class<? extends Action<P,R>> actionType) {
        return new Head<>(actionType);
    }

    @NonNull
    <S> ActionChain<P,S> then(@NonNull Class<? extends Action<R,S>> after);

    @NonNull
    <S> ActionChainOpt<P,S> thenOpt(@NonNull Class<? extends Action<R, Optional<S>>> after);
}
