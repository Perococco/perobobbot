package perococco.perobobbot.frontfx.action;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableBooleanValue;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.action.ActionBinding;
import perobobbot.lang.fp.Consumer0;

@RequiredArgsConstructor
public class PeroActionBinding implements ActionBinding {

    @NonNull
    private final BooleanProperty filteredProperty = new SimpleBooleanProperty();

    @NonNull
    private final ItemActionInfo itemActionInfo;

    @NonNull
    private final Consumer0 executable;

    @NonNull
    private final ObservableBooleanValue disabledProperty;

    @Override
    public @NonNull BooleanProperty filteredProperty() {
        return filteredProperty;
    }

    @Override
    public void bind() {
        this.itemActionInfo.bindDisable(filteredProperty.or(disabledProperty));
        this.itemActionInfo.bindAction(this::executeAction);
    }

    private void executeAction() {
        if (this.itemActionInfo.isDisabled() || filteredProperty.get()) {
            return;
        }
        executable.f();
    }

    @Override
    public void unbind() {
        this.itemActionInfo.unbindAction();
        this.itemActionInfo.unbindDisable();
    }
}
