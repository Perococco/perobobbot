package perobobbot.action;

import lombok.NonNull;
import perobobbot.lang.Nil;
import perobobbot.lang.fp.Function1;

import java.util.concurrent.CompletionStage;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

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
