package perobobbot.action;

import lombok.NonNull;

import java.util.Optional;

public interface ActionProvider {

    @NonNull
    <A> Optional<? extends A> findAction(@NonNull Class<A> actionType);

    @NonNull
    default <A> A getAction(@NonNull Class<A> actionType) {
        return findAction(actionType).orElseThrow(() -> new RuntimeException("Action not found '"+actionType+"'"));
    }
}
