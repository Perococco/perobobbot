package perococco.perobobbot.frontfx.gui.fxml;

import javafx.scene.control.Label;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.action.ActionExecutor;
import perobobbot.frontfx.action.list.LogOut;
import perobobbot.frontfx.model.FXApplicationIdentity;
import perobobbot.frontfx.model.state.ApplicationStateTool;
import perobobbot.frontfx.model.view.PluggableController;
import perobobbot.lang.Nil;

@FXMLController
@RequiredArgsConstructor
public class DashboardController implements PluggableController {

    private final @NonNull FXApplicationIdentity applicationIdentity;
    private final @NonNull ActionExecutor actionExecutor;

    public Label userLogin;


    @Override
    public void onShowing() {
        userLogin.textProperty().bind(applicationIdentity.binding(ApplicationStateTool::getUserLogin));
    }

    @Override
    public void onHiding() {
        userLogin.textProperty().unbind();
    }

    public void logOut() {
        actionExecutor.pushAction(LogOut.class, Nil.NIL);
    }
}
