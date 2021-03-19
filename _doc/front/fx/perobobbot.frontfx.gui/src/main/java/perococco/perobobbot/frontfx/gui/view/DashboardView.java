package perococco.perobobbot.frontfx.gui.view;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import perobobbot.frontfx.model.view.FXViewWithController;
import perobobbot.fx.FXLoaderFactory;
import perococco.perobobbot.frontfx.gui.fxml.DashboardController;

@Component
public class DashboardView extends FXViewWithController {

    public DashboardView(@NonNull FXLoaderFactory fxLoaderFactory) {
        super(fxLoaderFactory, DashboardController.class);
    }
}
