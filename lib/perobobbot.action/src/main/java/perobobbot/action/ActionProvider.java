package perobobbot.action;

import lombok.NonNull;

import java.util.Optional;

public interface ActionProvider {

    /**
     * @param actionType the class of the action
     * @param <A> the type of the action
     * @return an optional containing the action for the provided class if any, an empty optional otherwise
     */
    @NonNull
    <A> Optional<? extends A> findAction(@NonNull Class<A> actionType);

    /**
     * @param actionType the class of the action
     * @param <A> the type of the action
     * @return an optional containing the action for the provided class if it exists
     * @throws IllegalArgumentException if the action is unknown
     */
    @NonNull
    default <A> A getAction(@NonNull Class<A> actionType) {
        return findAction(actionType).orElseThrow(() -> new IllegalArgumentException("Action not found '"+actionType+"'"));
    }
}
