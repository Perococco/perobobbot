package perococco.perobobbot.action;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.action.Action;
import perobobbot.action.ActionChain;
import perobobbot.action.ActionChainOpt;

import java.util.Optional;

@RequiredArgsConstructor
public abstract class ActionChainBase<P, R> implements ActionChain<P, R> {

    @Override
    public @NonNull <S> ActionChain<P, S> then(@NonNull Class<? extends Action<R, S>> after) {
        return new PeroActionChain<>(getInitialAction(), this::launch, after);
    }

    @Override
    public @NonNull <S> ActionChainOpt<P, S> thenOpt(@NonNull Class<? extends Action<R, Optional<S>>> after) {
        return new PeroActionChainOpt<>(getInitialAction(), (e, p) -> launch(e, p).thenApply(Optional::of), after, s -> s);
    }
}
