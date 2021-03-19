package perobobbot.frontfx.model.view;

import javafx.scene.Node;
import lombok.NonNull;
import perobobbot.lang.fp.Consumer0;
import perobobbot.lang.fp.Consumer1;

import java.util.function.Consumer;

public interface SlotRegistry {

    /**
     * Create a view slot
     * @param name the name of this view slot
     * @param nodeSetter the method used to set the node of the view in this slot (link a {@link javafx.scene.layout.BorderPane#setCenter(Node)})
     * @param nodeClearer clear the node int the slot.
     */
    @NonNull
    void register(@NonNull String name, @NonNull Consumer1<? super Node> nodeSetter, @NonNull Consumer0 nodeClearer);

    default void register(@NonNull String name, @NonNull Consumer<? super Node> nodeSetter) {
        register(name, nodeSetter::accept, () -> nodeSetter.accept(null));
    }
}
