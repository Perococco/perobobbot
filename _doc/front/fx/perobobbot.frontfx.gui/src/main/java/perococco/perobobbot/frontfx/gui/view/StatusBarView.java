package perococco.perobobbot.frontfx.gui.view;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import perobobbot.frontfx.model.view.FXViewWithController;
import perobobbot.fx.FXLoaderFactory;
import perococco.perobobbot.frontfx.gui.fxml.StatusBarController;

@Component
public class StatusBarView extends FXViewWithController {

    public StatusBarView(@NonNull FXLoaderFactory fxLoaderFactory) {
        super(fxLoaderFactory, StatusBarController.class);
    }
}