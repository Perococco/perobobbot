package perococco.perobobbot.frontfx.gui;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import perobobbot.action.Action;
import perobobbot.frontfx.model.ApplicationIdentity;
import perobobbot.frontfx.model.ApplicationIdentityOperation;
import perobobbot.frontfx.model.FXApplicationIdentity;
import perobobbot.frontfx.model.state.ApplicationState;
import perobobbot.frontfx.model.state.ApplicationStateTool;
import perobobbot.frontfx.model.state.BasicApplicationStateTool;
import perobobbot.frontfx.model.state.EmptyApplicationStateTool;
import perobobbot.lang.Operator;
import perobobbot.lang.Subscription;
import perobobbot.lang.fp.Function1;

import java.util.concurrent.CompletionStage;

@Component
public class GUIFXApplicationIdentity implements FXApplicationIdentity {

    @NonNull
    private final ObservableValue<ApplicationStateTool> applicationStateObservable;

    @NonNull
    private final ApplicationIdentityOperation operation;

    public GUIFXApplicationIdentity(@NonNull ApplicationIdentity operation) {
        this.applicationStateObservable = operation.asFXObservable(EmptyApplicationStateTool.create(), BasicApplicationStateTool::new);
        this.operation = operation;
    }

    @Override
    public ApplicationStateTool getState() {
        return applicationStateObservable.getValue();
    }

    @Override
    public @NonNull BooleanBinding disabledProperty(@NonNull Class<? extends Action<?, ?>> actionType) {
        return Bindings.createBooleanBinding(() -> applicationStateObservable.getValue().isDisabled(actionType),applicationStateObservable);
    }

    @Override
    public @NonNull <T> ObjectBinding<T> binding(@NonNull Function1<? super ApplicationStateTool, ? extends T> transformer) {
        return Bindings.createObjectBinding(() -> transformer.f(applicationStateObservable.getValue()),applicationStateObservable);
    }

    @Override
    public @NonNull Subscription addListener(@NonNull ChangeListener<ApplicationStateTool> listener) {
        applicationStateObservable.addListener(listener);
        return () -> applicationStateObservable.removeListener(listener);
    }

    @Override
    public void bindProperty(@NonNull Property<ApplicationStateTool> property) {
        property.bind(applicationStateObservable);
    }

    @Override
    public @NonNull <T> CompletionStage<T> operate(@NonNull Operator<ApplicationState, T> operator) {
        return operation.operate(operator);
    }
}
