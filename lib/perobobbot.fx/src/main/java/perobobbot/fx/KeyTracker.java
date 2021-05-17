package perobobbot.fx;

import javafx.scene.Node;
import javafx.stage.Stage;
import lombok.NonNull;
import perobobbot.lang.Subscription;

/**
 * @author perococco
 */
public interface KeyTracker extends ReadOnlyKeyTracker {

    @NonNull
    static KeyTracker create() {
        return KeyTrackerFactory.getInstance().create();
    }

    void attach(@NonNull Stage target);

    void detach();

    @NonNull
    Subscription addKeyCatcher(Node node, KeyCatcher keyCatcher);

    @NonNull
    Subscription addKeyCatcherOnlyIfOver(Node node, KeyCatcher keyCatcher);

}
