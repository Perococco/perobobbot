package perococco.perobobbot.frontfx.gui;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import perobobbot.frontfx.model.dialog.DialogManager;
import perobobbot.fx.KeyCatcher;
import perobobbot.fx.ReadOnlyKeyTracker;

@Component
@RequiredArgsConstructor
@Qualifier("mainRoot")
public class KeyTracking implements KeyCatcher {

    @NonNull
    private final DialogManager dialogManager;

    @Override
    public void onKeyEvent(@NonNull KeyEvent event, @NonNull ReadOnlyKeyTracker keyTracker) {
        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            handleKeyPressed(event,keyTracker);
        }
    }

    private void handleKeyPressed(KeyEvent event, ReadOnlyKeyTracker keyTracker) {
        if (event.getCode() == KeyCode.F10) {
            if (keyTracker.arePressed(KeyCode.SHIFT, KeyCode.CONTROL)) {
                dialogManager.showThemeSelector();
            }
        }
    }
}
