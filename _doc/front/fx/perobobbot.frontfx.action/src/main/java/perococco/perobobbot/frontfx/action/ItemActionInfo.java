package perococco.perobobbot.frontfx.action;

import javafx.beans.value.ObservableValue;
import lombok.NonNull;

public interface ItemActionInfo {

    /**
     * Bind the disabled property of the item associated with this with the provided observable
     * @param observableValue tha observable to bind
     */
    void bindDisable(@NonNull ObservableValue<? extends Boolean> observableValue);

    /**
     * unbind the disabled porperty of the item associated with this
     */
    void unbindDisable();

    /**
     * @return true if the item associated with this is disabled
     */
    boolean isDisabled();

    /**
     * bind an executable everytime the item is executed (wich is item dependent, for button this is when the user click on it for instance)
     * @param executable the action to execute
     */
    void bindAction(@NonNull Runnable executable);

    /**
     * unbind a previous bound action
     */
    void unbindAction();

}
