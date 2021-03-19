package perococco.perobobbot.frontfx.action.iteminfo;

import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import lombok.NonNull;

public abstract class NodeBaseItemActionInfo<N extends Node> extends ItemActionInfoBase<N> {

    public NodeBaseItemActionInfo(@NonNull N item) {
        super(item);
    }

    @Override
    protected boolean isDisabled(@NonNull N item) {
        return item.isDisabled();
    }

    @Override
    protected void bindDisable(@NonNull N item, @NonNull ObservableValue<? extends Boolean> observableValue) {
        item.disableProperty().bind(observableValue);
    }

    @Override
    protected void unbindDisable(@NonNull N item) {
        item.disableProperty().unbind();
    }

}
