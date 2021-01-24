package perococco.perobobbot.action;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.action.Action;
import perobobbot.action.ActionExecutor;
import perobobbot.lang.fp.Function2;

import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
public class PeroActionChain<P, U, R> extends ActionChainBase<P, R> {

    @Getter
    private final Class<? extends Action<?,?>> initialAction;

    private final Function2<? super ActionExecutor, ? super P, ? extends CompletionStage<U>> before;

    private final @NonNull Class<? extends Action<U, R>> then;

    @Override
    public @NonNull CompletionStage<R> launch(@NonNull ActionExecutor executor, @NonNull P parameter) {
        return before.f(executor, parameter)
                     .thenCompose(r -> executor.pushAction(then, r));
    }

}
