package perococco.perobobbot.frontfx.model.view;

import javafx.scene.Node;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.frontfx.model.view.FXViewInstance;
import perobobbot.frontfx.model.view.PluggableController;
import perobobbot.fx.FXLoadingResult;

import java.util.Optional;

@RequiredArgsConstructor
public class PeroFXViewInstance implements FXViewInstance {

    @NonNull
    private final FXLoadingResult result;

    @Override
    public void showing() {
        result.getController(PluggableController.class).ifPresent(PluggableController::onShowing);
    }

    @Override
    public void hiding() {
        result.getController(PluggableController.class).ifPresent(PluggableController::onHiding);
    }

    @Override
    public @NonNull Optional<Node> node() {
        return result.getRoot(Node.class);
    }
}
