package perobobbot.action;

import lombok.NonNull;

import java.util.concurrent.CompletionStage;

public interface ActionExecutor {

    <P, R> @NonNull CompletionStage<R> pushAction(@NonNull Class<? extends Action<? super P, ? extends R>> action, @NonNull P parameter);

    <P, R> @NonNull CompletionStage<R> pushAction(@NonNull Action<? super P, ? extends R> action, @NonNull P parameter);

}
