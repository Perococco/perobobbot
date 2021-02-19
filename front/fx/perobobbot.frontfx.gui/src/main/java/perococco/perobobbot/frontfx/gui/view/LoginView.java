package perococco.perobobbot.frontfx.gui.view;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import perobobbot.frontfx.model.view.FXViewWithController;
import perobobbot.fx.FXLoaderFactory;
import perococco.perobobbot.frontfx.gui.fxml.LoginViewController;

@Component
public class LoginView extends FXViewWithController {

    public LoginView(@NonNull FXLoaderFactory fxLoaderFactory) {
        super(fxLoaderFactory, LoginViewController.class);
    }
}
