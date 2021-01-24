package perococco.perobobbot.frontfx.action.iteminfo;

import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import lombok.NonNull;

public abstract class NodeBaseItemInfo<N extends Node> extends ItemInfoBase<N> {

    public NodeBaseItemInfo(@NonNull N item) {
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
