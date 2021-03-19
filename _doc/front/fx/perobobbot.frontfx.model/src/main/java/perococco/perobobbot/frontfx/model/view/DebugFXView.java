package perococco.perobobbot.frontfx.model.view;

import javafx.scene.Node;
import lombok.NonNull;
import perobobbot.frontfx.model.view.FXView;
import perobobbot.fx.NodeWrapper;
import perobobbot.lang.fp.Function2;


public class DebugFXView implements Function2<FXView, Node, Node> {

    private final NodeWrapper nodeMapper = new NodeWrapper();

    @NonNull
    @Override
    public Node f(@NonNull FXView fxView, @NonNull Node node) {
        return nodeMapper.f(fxView.getClass().getSimpleName(), node);
    }

}
