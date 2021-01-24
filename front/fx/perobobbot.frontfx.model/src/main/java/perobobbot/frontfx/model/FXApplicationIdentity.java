package perobobbot.frontfx.model;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import lombok.NonNull;
import perobobbot.action.Action;
import perobobbot.frontfx.model.state.ApplicationStateTool;
import perobobbot.lang.Subscription;
import perobobbot.lang.fp.Consumer1;
import perobobbot.lang.fp.Function1;

public interface FXApplicationIdentity extends ApplicationIdentityOperation {

    ApplicationStateTool getState();

    @NonNull
    BooleanBinding disabledProperty(@NonNull Class<? extends Action<?,?>> actionType);

    @NonNull <T> ObjectBinding<T> binding(@NonNull Function1<? super ApplicationStateTool, ? extends T> transformer);

    @NonNull
    Subscription addListener(@NonNull ChangeListener<ApplicationStateTool> listener);

    @NonNull
    default Subscription addListenerAndCall(@NonNull Consumer1<ApplicationStateTool> listener) {
        final Subscription subscription = addListener(listener);
        listener.accept(getState());
        return subscription;
    }

    @NonNull
    default Subscription addListenerAndCall(@NonNull Runnable listener) {
        final Subscription subscription = addListener(listener);
        listener.run();
        return subscription;
    }

    @NonNull
    default Subscription addListener(@NonNull Consumer1<ApplicationStateTool> listener) {
        return addListener((l, o, n) -> listener.accept(n));
    }

    @NonNull
    default Subscription addListener(@NonNull Runnable listener) {
        return addListener((l, o, n) -> listener.run());
    }

    void bindProperty(@NonNull Property<ApplicationStateTool> property);


    @NonNull
    default void addWeakListener(@NonNull ChangeListener<ApplicationStateTool> listener) {
        addListener(new WeakChangeListener<>(listener));
    }
    @NonNull
    default void addWeakListener(@NonNull Consumer1<ApplicationStateTool> listener) {
        addWeakListener((l, o, n) -> listener.accept(n));
    }
    @NonNull
    default void addWeakListener(@NonNull Runnable listener) {
        addWeakListener((l, o, n) -> listener.run());
    }


}
