package perobobbot.action;

import lombok.NonNull;

import java.util.concurrent.CompletionStage;

/**
 * Executor of action. An action is a class and can be executed with a parameter
 */
public interface ActionExecutor {

    /**
     * Push an action by using its type
     * @param action the class of the action to execute
     * @param parameter the parameter to pass to the action
     * @param <P> the type of the parameter
     * @param <R> the type of the result of the action
     * @return a {@link CompletionStage} that will contain the result of the action
     */
    <P, R> @NonNull CompletionStage<R> pushAction(@NonNull Class<? extends Action<? super P, ? extends R>> action, @NonNull P parameter);

    /**
     * Push an instance of an action
     * @param action the action to execute
     * @param parameter the parameter to pass to the action
     * @param <P> the type of the parameter
     * @param <R> the type of the result of the action
     * @return a {@link CompletionStage} that will contain the result of the action
     */
    <P, R> @NonNull CompletionStage<R> pushAction(@NonNull Action<? super P, ? extends R> action, @NonNull P parameter);

}
