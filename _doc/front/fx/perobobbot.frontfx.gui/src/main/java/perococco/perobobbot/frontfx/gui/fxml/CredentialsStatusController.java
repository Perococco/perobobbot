package perococco.perobobbot.frontfx.gui.fxml;

import lombok.NonNull;
import perobobbot.action.ActionExecutor;
import perobobbot.frontfx.model.FXApplicationIdentity;
import perobobbot.frontfx.model.state.ApplicationStateTool;
import perobobbot.frontfx.model.view.PluggableControllerBase;

public class CredentialsStatusController extends PluggableControllerBase {

    public CredentialsStatusController(@NonNull FXApplicationIdentity applicationIdentity, @NonNull ActionExecutor actionExecutor) {
        super(applicationIdentity, actionExecutor);
    }

    @Override
    protected void onApplicationStateChanged(@NonNull ApplicationStateTool tool) {

    }
}
