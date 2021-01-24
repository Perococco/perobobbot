package perococco.perobobbot.action;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.action.Action;
import perobobbot.action.ActionChain;
import perobobbot.action.ActionChainOpt;
import perobobbot.lang.Nil;

import java.util.Optional;

@RequiredArgsConstructor
public abstract class ActionChainOptBase<P, R> implements ActionChainOpt<P, R> {


    @Override
    public @NonNull <S> ActionChainOpt<P, S> then(@NonNull Class<? extends Action<R, S>> actionType) {
        return new PeroActionChainOpt<>(getInitialAction(), this::launch, actionType, Optional::of);
    }

    @Override
    public @NonNull <S> ActionChainOpt<P, S> thenFlat(@NonNull Class<? extends Action<R, Optional<S>>> actionType) {
        return new PeroActionChainOpt<>(getInitialAction(), this::launch, actionType, s -> s);
    }

    @Override
    public @NonNull <S> ActionChain<P, S> thenOr(@NonNull Class<? extends Action<R, S>> actionType,
                                                 @NonNull Class<? extends Action<Nil, S>> or) {
        return new PeroActionChainOpt2<>(getInitialAction(), this::launch, actionType, or);
    }
}
