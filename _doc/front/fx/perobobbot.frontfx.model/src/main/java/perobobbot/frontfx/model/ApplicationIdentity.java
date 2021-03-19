package perobobbot.frontfx.model;

import javafx.beans.value.ObservableValue;
import lombok.NonNull;
import perobobbot.frontfx.model.state.ApplicationState;
import perobobbot.frontfx.model.state.DataState;
import perobobbot.lang.AsyncIdentity;
import perobobbot.lang.Mutation;
import perobobbot.lang.fp.Function1;
import perococco.perobobbot.frontfx.model.DefaultApplicationIdentity;

import java.util.concurrent.CompletionStage;

/**
 * Alias class to {@link AsyncIdentity} for the state {@link ApplicationState}
 */
public interface ApplicationIdentity extends AsyncIdentity<ApplicationState>, ApplicationIdentityOperation {

    static @NonNull ApplicationIdentity with(@NonNull ApplicationState applicationState) {
        return new DefaultApplicationIdentity(applicationState);
    }

    @NonNull
    default CompletionStage<ApplicationState> mutateDataState(@NonNull Mutation<DataState> mutation) {
        return mutate(s -> {
            final var dataState = s.getDataState();
            final var newDataState = mutation.mutate(dataState);
            if (dataState == newDataState) {
                return s;
            }
            return s.toBuilder().dataState(newDataState).build();
        });
    }


    @NonNull <T> ObservableValue<T> asFXObservable(@NonNull Function1<? super ApplicationState, ? extends T> getter);
}
