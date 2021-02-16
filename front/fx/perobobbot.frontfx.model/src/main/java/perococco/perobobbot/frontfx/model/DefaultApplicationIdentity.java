package perococco.perobobbot.frontfx.model;

import javafx.beans.value.ObservableValue;
import lombok.NonNull;
import perobobbot.frontfx.model.ApplicationIdentity;
import perobobbot.frontfx.model.state.ApplicationState;
import perobobbot.fx.ObservableIdentityValue;
import perobobbot.lang.ProxyAsyncIdentity;
import perobobbot.lang.fp.Function1;

public class DefaultApplicationIdentity extends ProxyAsyncIdentity<ApplicationState> implements ApplicationIdentity {

    public DefaultApplicationIdentity(@NonNull ApplicationState initialValue) {
        super(initialValue);
    }

    @Override
    public @NonNull <T> ObservableValue<T> asFXObservable(@NonNull T initialValue, @NonNull Function1<? super ApplicationState, ? extends T> getter) {
        final ObservableIdentityValue<ApplicationState,T> observable = new ObservableIdentityValue<>(getter.apply(this.getState()), getter);
        addWeakListener(observable);
        return observable;
    }
}
