package perobobbot.fx.dialog;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableBooleanValue;
import lombok.NonNull;

public abstract class AbstractDialogController<I,O> implements DialogController<I,O> {

    private final ObjectProperty<DialogState<O>> resultProperty = new SimpleObjectProperty<>(DialogState.empty());

    private final BooleanProperty extraInvalidCondition = new SimpleBooleanProperty(false);

    private final ObservableBooleanValue invalidProperty;

    public AbstractDialogController() {
        invalidProperty = Bindings.createBooleanBinding(() -> !resultProperty.get().isValid(),resultProperty)
                                  .or(extraInvalidCondition);
    }

    protected void setDialogState(@NonNull DialogState<O> dialogState) {
        resultProperty.set(dialogState);
    }

    @Override
    public @NonNull ObjectProperty<DialogState<O>> dialogStateProperty() {
        return resultProperty;
    }

    @Override
    public @NonNull ObservableBooleanValue invalidPropertyProperty() {
        return invalidProperty;
    }
}
