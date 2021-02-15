package perobobbot.action;

import lombok.NonNull;
import perococco.perobobbot.action.Head;

import java.util.Optional;

/**
 * A chain of action. The chain is started with the method {@link #head(Class)} and completed
 * with either {@link #then(Class)} or {@link #thenOpt(Class)}
 * @param <P> the type of the parameter of the input of the chain
 * @param <R> the type of the parameter of the output of the chain
 */
public interface ActionChain<P,R> extends Launchable<P,R> {

    /**
     * Start a new chain
     * @param actionType the type of the action
     * @param <P> the type of the input of the action
     * @param <R> the type of the ouptu of the action
     * @return an action chain with only the provided action.
     */
    @NonNull
    static <P,R> ActionChain<P,R> head(@NonNull Class<? extends Action<P,R>> actionType) {
        return new Head<>(actionType);
    }

    /**
     * Append an action that will be executed after the previous action in the chain if it is successful.
     * @param after the action to execute after the previous action in the chain
     * @param <S> the type of the ouput of the provided action
     * @return a new chain including the new action <code>after</code>
     */
    @NonNull
    <S> ActionChain<P,S> then(@NonNull Class<? extends Action<R,S>> after);

    /**
     * Same as {@link #then} but for action returning an optional
     */
    @NonNull
    <S> ActionChainOpt<P,S> thenOpt(@NonNull Class<? extends Action<R, Optional<S>>> after);
}
