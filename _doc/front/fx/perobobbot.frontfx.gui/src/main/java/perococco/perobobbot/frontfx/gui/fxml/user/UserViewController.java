package perococco.perobobbot.frontfx.gui.fxml.user;

import javafx.scene.control.ListView;
import lombok.NonNull;
import perobobbot.action.ActionExecutor;
import perobobbot.frontfx.model.FXApplicationIdentity;
import perobobbot.frontfx.model.state.ApplicationStateTool;
import perobobbot.frontfx.model.view.PluggableControllerBase;
import perobobbot.fx.FXListCell;
import perobobbot.fx.FXLoaderFactory;
import perobobbot.security.com.SimpleUser;
import perococco.perobobbot.frontfx.gui.fxml.FXMLController;

@FXMLController
public class UserViewController extends PluggableControllerBase {

    private final @NonNull FXLoaderFactory fxLoaderFactory;

    public ListView<SimpleUser> userList;

    public UserViewController(@NonNull FXApplicationIdentity applicationIdentity,
                              @NonNull ActionExecutor actionExecutor,
                              @NonNull FXLoaderFactory fxLoaderFactory) {
        super(applicationIdentity, actionExecutor);
        this.fxLoaderFactory = fxLoaderFactory;

    }

    public void initialize() {
        this.userList.setCellFactory(d -> FXListCell.create(fxLoaderFactory,UserCellController.class));
    }

    @Override
    protected void onApplicationStateChanged(@NonNull ApplicationStateTool tool) {
        userList.getItems().setAll(tool.getUsers());
    }


}
