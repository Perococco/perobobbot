package perococco.perobobbot.frontfx.action.iteminfo;

import javafx.beans.value.ObservableValue;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

/**
 * Binder on an object. Method with not bind anything, only warn that the binding is not possible
 */
@Log4j2
public class ObjectBinder extends ItemActionInfoBase<Object> {

    public ObjectBinder(@NonNull Object item) {
        super(item);
    }

    @Override
    public void bindDisable(@NonNull Object item, @NonNull ObservableValue<? extends Boolean> observableValue) {
        LOG.warn("Binding on an unknown item. No disable property available to bind. Item type : {}",item.getClass());
    }

    @Override
    protected void unbindDisable(@NonNull Object item) {    }

    @Override
    protected void bindAction(@NonNull Object item, @NonNull Runnable executable) {
        LOG.warn("Binding on an unknown item. No action available to bind the executable. Item type : {}",item.getClass());
    }

    @Override
    protected void unbindAction(@NonNull Object item) {}

    @Override
    protected boolean isDisabled(@NonNull Object item) {
        return true;
    }
}
