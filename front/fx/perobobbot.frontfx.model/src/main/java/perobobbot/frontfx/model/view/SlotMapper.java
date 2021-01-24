package perobobbot.frontfx.model.view;

import javafx.scene.Node;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.fp.Consumer0;
import perobobbot.lang.fp.Consumer1;
import perobobbot.lang.fp.Function2;

import java.util.Map;

@RequiredArgsConstructor
public class SlotMapper implements SlotRegistry {

    private final Map<String, ViewSlot> mapping;

    @NonNull
    private final Function2<FXView, Node,Node> nodeMapper;

    @Override
    public @NonNull void register(@NonNull String name,
                                      @NonNull Consumer1<? super Node> nodeSetter,
                                      @NonNull Consumer0 nodeClearer) {
        final var viewSlot = new ViewSlot(name, nodeSetter, nodeClearer, nodeMapper);
        this.mapping.put(name,viewSlot);
    }

}
