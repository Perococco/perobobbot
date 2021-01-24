package perobobbot.frontfx.model;

import javafx.beans.value.ObservableValue;
import lombok.NonNull;
import perobobbot.frontfx.model.state.ApplicationState;
import perobobbot.lang.AsyncIdentity;
import perobobbot.lang.fp.Function1;
import perococco.perobobbot.frontfx.model.DefaultApplicationIdentity;

public interface ApplicationIdentity extends AsyncIdentity<ApplicationState>, ApplicationIdentityOperation {

    static @NonNull ApplicationIdentity with(@NonNull ApplicationState applicationState) {
        return new DefaultApplicationIdentity(applicationState);
    }

    @NonNull <T> ObservableValue<T> asFXObservable(@NonNull T initialValue, @NonNull Function1<? super ApplicationState, ? extends T> getter);
}
