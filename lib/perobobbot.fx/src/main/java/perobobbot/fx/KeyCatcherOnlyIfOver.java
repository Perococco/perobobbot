package perobobbot.fx;

import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class KeyCatcherOnlyIfOver implements KeyCatcher {

    @NonNull
    private final Node node;

    @NonNull
    private final KeyCatcher keyCatcher;


    @Override
    public void onKeyEvent(@NonNull KeyEvent event, @NonNull ReadOnlyKeyTracker keyTracker) {
        if (keyTracker.isMouseOver(node)) {
            keyCatcher.onKeyEvent(event,keyTracker);
        }
    }
}
