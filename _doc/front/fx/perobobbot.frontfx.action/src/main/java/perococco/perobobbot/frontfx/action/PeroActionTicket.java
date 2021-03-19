package perococco.perobobbot.frontfx.action;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.action.Action;
import perobobbot.action.ActionExecutor;
import perobobbot.action.ActionTicket;
import perobobbot.lang.Nil;
import perobobbot.lang.fp.Function1;

import java.util.concurrent.CompletionStage;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class PeroActionTicket<R> implements ActionTicket<R> {

    private final ActionExecutor actionExecutor;

    @NonNull
    private final CompletionStage<R> completionStage;

    @Override
    public @NonNull ActionTicket<R> whenComplete(@NonNull BiConsumer<? super R, ? super Throwable> action) {
        return withNewCompletionStage(completionStage.whenComplete(action));
    }

    @Override
    public @NonNull ActionTicket<Nil> thenAccept(@NonNull Consumer<? super R> action) {
        return withNewCompletionStage(completionStage.thenAccept(action).thenApply(v -> Nil.NIL));
    }

    private <T> ActionTicket<T> withNewCompletionStage(@NonNull CompletionStage<T> completionStage) {
        return new PeroActionTicket<>(actionExecutor, completionStage);
    }

    @Override
    public @NonNull <S> ActionTicket<S> thenApply(@NonNull Function1<? super R, ? extends S> after) {
        return withNewCompletionStage(completionStage.thenApply(after));
    }

    @Override
    public @NonNull <S> ActionTicket<S> thenExecute(@NonNull Class<? extends Action<R, S>> actionType) {
        return withNewCompletionStage(completionStage.thenCompose(r -> actionExecutor.pushAction(actionType,r)));
    }

    @Override
    public @NonNull CompletionStage<R> asCompletionStage() {
        return completionStage;
    }
}
