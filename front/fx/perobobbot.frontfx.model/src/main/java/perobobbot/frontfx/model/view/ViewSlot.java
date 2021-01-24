package perobobbot.frontfx.model.view;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import lombok.Getter;
import lombok.NonNull;
import perobobbot.lang.fp.Consumer0;
import perobobbot.lang.fp.Consumer1;
import perobobbot.lang.fp.Function2;

public class ViewSlot {

    @NonNull
    @Getter
    private final String name;

    @NonNull
    private final Consumer1<? super Node> setNode;
    @NonNull
    private final Consumer0 clearNode;

    @NonNull
    private final Function2<? super FXView, ? super Node, ? extends Node> nodeMapper;

    @NonNull
    private FXViewInstance viewInstance = FXViewInstance.EMPTY;

    private final ObjectProperty<FXView> fxView = new SimpleObjectProperty<>(EmptyFXView.create());

    public ViewSlot(@NonNull String name,
                    @NonNull Consumer1<? super Node> setNode,
                    @NonNull Consumer0 clearNode,
                    @NonNull Function2<? super FXView, ? super Node, ? extends Node> nodeMapper) {
        this.name = name;
        this.clearNode = clearNode;
        this.setNode = setNode;
        this.nodeMapper = nodeMapper;
        this.fxView.addListener((l,o,n) -> onViewChange(n));
    }

    public ViewSlot(@NonNull String name, @NonNull Consumer1<? super Node> setNode, @NonNull Consumer0 clearNode) {
        this(name,setNode,clearNode,(f,n) -> n);
    }

    private void onViewChange(FXView newView) {
        final FXViewInstance oldViewInstance = this.viewInstance;

        this.viewInstance = newView.getViewInstance();
        oldViewInstance.hiding();
        this.viewInstance.showing();
        viewInstance.node()
                    .map(node -> nodeMapper.apply(newView,node))
                    .ifPresentOrElse(setNode,clearNode);
    }

    @NonNull
    public FXView getFxView() {
        return fxView.get();
    }

    @NonNull
    public ObjectProperty<FXView> fxViewProperty() {
        return fxView;
    }

    public void setFxView(FXView fxView) {
        this.fxView.set(fxView);
    }
}
