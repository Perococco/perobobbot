package perobobbot.fx;

import javafx.scene.input.KeyEvent;
import lombok.NonNull;

/**
 * @author perococco
 */
public interface KeyCatcher {

    void onKeyEvent(@NonNull KeyEvent event, @NonNull ReadOnlyKeyTracker keyTracker);

}
