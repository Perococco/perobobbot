package perobobbot.fx;

import javafx.scene.input.KeyEvent;
import lombok.NonNull;

/**
 * @author Perococco
 */
public interface KeyCatcher {

    void onKeyEvent(@NonNull KeyEvent event, @NonNull ReadOnlyKeyTracker keyTracker);

}
