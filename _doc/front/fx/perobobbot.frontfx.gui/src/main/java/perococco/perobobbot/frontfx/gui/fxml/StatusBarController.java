package perococco.perobobbot.frontfx.gui.fxml;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
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
public class StatusBarController implements PluggableController {

    private final @NonNull ActionExecutor actionExecutor;

    public Label loginLabel;

    private final @NonNull FXApplicationIdentity identity;

    public void initialize() {}

    @Override
    public void onShowing() {
        loginLabel.textProperty().bind(identity.binding(ApplicationStateTool::getUserLogin));
    }

    @Override
    public void onHiding() {
        loginLabel.textProperty().unbind();
    }

    public void logOut() {
        actionExecutor.pushAction(LogOut.class, Nil.NIL);
    }
}
