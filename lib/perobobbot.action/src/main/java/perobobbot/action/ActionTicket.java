package perobobbot.action;

import lombok.NonNull;
import perobobbot.lang.Nil;
import perobobbot.lang.fp.Function1;

import java.util.concurrent.CompletionStage;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * This a completion stage (that can be retrieve with {@link #asCompletionStage()} extended
 * with the action mechanism to be able to chain actions.
 * @param <R> the type of the result
 */
public interface ActionTicket<R> {

    @NonNull
    ActionTicket<R> whenComplete(@NonNull BiConsumer<? super R, ? super Throwable> action);

    @NonNull
    ActionTicket<Nil> thenAccept(@NonNull Consumer<? super R> action);

    @NonNull
    <S> ActionTicket<S> thenApply(@NonNull Function1<? super R, ? extends S> after);

    @NonNull
    <S> ActionTicket<S> thenExecute(@NonNull Class<? extends Action<R,S>> actionType);

    @NonNull
    CompletionStage<R> asCompletionStage();
}
