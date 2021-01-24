package perobobbot.action;

import lombok.NonNull;
import perobobbot.lang.Nil;

/**
 *
 */
public interface ActionManager extends ActionLauncher {

    @NonNull <P, R> ActionBinder<P> binder(@NonNull Launchable<P, R> actionType);

    @NonNull <R> NilActionBinder nilBinder(@NonNull Launchable<Nil, R> actionType);

    default @NonNull <P, R> ActionBinder<P> binder(@NonNull Class<? extends Action<P, R>> actionType) {
        return binder(Launchable.single(actionType));
    }

    default @NonNull <R> NilActionBinder nilBinder(@NonNull Class<? extends Action<Nil, R>> actionType) {
        return nilBinder(Launchable.single(actionType));
    }

    default @NonNull <R> ActionBinding createBinding(@NonNull Class< ? extends Action<Nil,R>> actionType, @NonNull Object item) {
        return nilBinder(actionType).createBinding(item);
    }

}
