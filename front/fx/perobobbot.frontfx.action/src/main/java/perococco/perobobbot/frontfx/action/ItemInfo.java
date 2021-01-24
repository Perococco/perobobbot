package perococco.perobobbot.frontfx.action;

import javafx.beans.value.ObservableValue;
import lombok.NonNull;

public interface ItemInfo {

    void bindDisable(@NonNull ObservableValue<? extends Boolean> observableValue);

    void unbindDisable();

    boolean isDisabled();

    void bindAction(@NonNull Runnable executable);

    void unbindAction();

}
