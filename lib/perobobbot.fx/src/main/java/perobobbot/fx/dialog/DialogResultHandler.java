package perobobbot.fx.dialog;

import lombok.NonNull;

public interface DialogResultHandler<O> {

    default void onCancelled() {}

    default void onApplied(@NonNull O value) {}

    default void onValidated(@NonNull O value) {}

}
