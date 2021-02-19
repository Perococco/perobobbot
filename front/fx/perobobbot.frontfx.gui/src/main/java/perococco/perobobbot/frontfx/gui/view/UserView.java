package perococco.perobobbot.frontfx.gui.view;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import perobobbot.frontfx.model.view.FXViewWithController;
import perobobbot.fx.FXLoaderFactory;
import perococco.perobobbot.frontfx.gui.fxml.user.UserViewController;

@Component
public class UserView extends FXViewWithController {

    public UserView(@NonNull FXLoaderFactory fxLoaderFactory) {
        super(fxLoaderFactory, UserViewController.class);
    }
}
