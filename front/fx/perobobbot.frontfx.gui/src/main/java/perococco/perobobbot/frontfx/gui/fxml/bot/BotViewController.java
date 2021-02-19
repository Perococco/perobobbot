package perococco.perobobbot.frontfx.gui.fxml.bot;

import javafx.scene.control.ListView;
import lombok.NonNull;
import perobobbot.action.ActionExecutor;
import perobobbot.frontfx.model.FXApplicationIdentity;
import perobobbot.frontfx.model.state.ApplicationStateTool;
import perobobbot.frontfx.model.view.PluggableControllerBase;
import perobobbot.fx.FXListCell;
import perobobbot.fx.FXLoaderFactory;
import perobobbot.lang.Bot;
import perococco.perobobbot.frontfx.gui.fxml.FXMLController;

@FXMLController
public class BotViewController extends PluggableControllerBase {

    private final @NonNull FXLoaderFactory fxLoaderFactory;

    public ListView<Bot> botList;

    public BotViewController(@NonNull FXApplicationIdentity applicationIdentity,
                             @NonNull ActionExecutor actionExecutor,
                             @NonNull FXLoaderFactory fxLoaderFactory) {
        super(applicationIdentity, actionExecutor);
        this.fxLoaderFactory = fxLoaderFactory;
    }

    public void initialize() {
        botList.setCellFactory(l -> FXListCell.create(fxLoaderFactory,BotCellController.class));
    }

    protected void onApplicationStateChanged(@NonNull ApplicationStateTool tool) {
        botList.getItems().setAll(tool.getBots());
    }


}
