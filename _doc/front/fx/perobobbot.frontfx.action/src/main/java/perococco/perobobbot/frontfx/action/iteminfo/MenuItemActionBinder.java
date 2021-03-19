package perococco.perobobbot.frontfx.action.iteminfo;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.MenuItem;
import lombok.NonNull;

public class MenuItemActionBinder extends ItemActionInfoBase<MenuItem> {

    public MenuItemActionBinder(@NonNull MenuItem item) {
        super(item);
    }

    @Override
    protected void bindDisable(@NonNull MenuItem item, @NonNull ObservableValue<? extends Boolean> observableValue) {
        item.disableProperty().bind(observableValue);
    }

    @Override
    protected void unbindDisable(@NonNull MenuItem item) {
        item.disableProperty().unbind();
    }

    @Override
    protected void bindAction(@NonNull MenuItem item, @NonNull Runnable executable) {
        item.setOnAction(e -> executable.run());
    }

    @Override
    protected void unbindAction(@NonNull MenuItem item) {
        item.setOnAction(e -> {});
    }

    @Override
    protected boolean isDisabled(@NonNull MenuItem item) {
        return item.isDisable();
    }
}
