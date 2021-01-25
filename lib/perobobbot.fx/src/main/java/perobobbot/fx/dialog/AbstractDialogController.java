package perobobbot.fx.dialog;

import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.beans.value.ObservableBooleanValue;
import lombok.NonNull;
import perobobbot.lang.Subscription;

public abstract class AbstractDialogController<I,O> implements DialogController<I,O> {

    private final ObjectProperty<DialogState<O>> resultProperty = new SimpleObjectProperty<>(DialogState.empty());

    private final BooleanProperty extraInvalidCondition = new SimpleBooleanProperty(false);

    private final StringProperty titleProperty = new SimpleStringProperty("");

    private final ObservableBooleanValue invalidProperty;

    public AbstractDialogController() {
        invalidProperty = Bindings.createBooleanBinding(() -> !resultProperty.get().isValid(),resultProperty)
                                  .or(extraInvalidCondition);
    }

    @Override
    public @NonNull Subscription initializeDialog(@NonNull I input) {
        this.setTitle(this.getInitialTitleDialog());
        return Subscription.NONE;
    }

    protected abstract @NonNull String getInitialTitleDialog();

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


    public @NonNull String getTitle() {
        return titleProperty.get();
    }

    public @NonNull StringProperty titleProperty() {
        return titleProperty;
    }

    public void setTitle(@NonNull String title) {
        this.titleProperty.set(title);
    }
}
