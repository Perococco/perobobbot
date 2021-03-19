package perococco.perobobbot.frontfx.gui.view;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import perobobbot.frontfx.model.view.FXViewWithController;
import perobobbot.fx.FXLoaderFactory;
import perococco.perobobbot.frontfx.gui.fxml.bot.BotViewController;

@Component
public class BotView extends FXViewWithController {

    public BotView(@NonNull FXLoaderFactory fxLoaderFactory) {
        super(fxLoaderFactory, BotViewController.class);
    }
}
