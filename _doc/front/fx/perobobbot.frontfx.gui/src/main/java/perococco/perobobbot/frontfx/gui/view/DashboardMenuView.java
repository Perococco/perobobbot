package perococco.perobobbot.frontfx.gui.view;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import perobobbot.frontfx.model.view.FXViewWithController;
import perobobbot.fx.FXLoaderFactory;
import perococco.perobobbot.frontfx.gui.fxml.DashboardMenuController;

@Component
public class DashboardMenuView extends FXViewWithController {

    public DashboardMenuView(@NonNull FXLoaderFactory fxLoaderFactory) {
        super(fxLoaderFactory, DashboardMenuController.class);
    }
}
