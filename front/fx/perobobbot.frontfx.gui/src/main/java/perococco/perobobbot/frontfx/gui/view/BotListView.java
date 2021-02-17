package perococco.perobobbot.frontfx.gui.view;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import perobobbot.frontfx.model.view.FXViewWithController;
import perobobbot.fx.FXLoaderFactory;
import perococco.perobobbot.frontfx.gui.fxml.BotListController;
import perococco.perobobbot.frontfx.gui.fxml.UserListController;

@Component
public class BotListView extends FXViewWithController {

    public BotListView(@NonNull FXLoaderFactory fxLoaderFactory) {
        super(fxLoaderFactory, BotListController.class);
    }
}
