package perococco.perobobbot.frontfx.gui.view;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import perobobbot.frontfx.model.view.FXViewWithController;
import perobobbot.fx.FXLoaderFactory;
import perococco.perobobbot.frontfx.gui.fxml.DashboardController;

@Component
public class DashboardFXView extends FXViewWithController {

    public DashboardFXView(@NonNull FXLoaderFactory fxLoaderFactory) {
        super(fxLoaderFactory, DashboardController.class);
    }
}
