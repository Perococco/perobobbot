package perococco.perobobbot.frontfx.gui.fxml;

import javafx.event.ActionEvent;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.action.ActionExecutor;
import perobobbot.frontfx.action.list.ChangeDashboardMainView;
import perobobbot.frontfx.action.list.RefreshBotList;
import perobobbot.frontfx.action.list.RefreshUserList;
import perobobbot.frontfx.model.FXApplicationIdentity;
import perobobbot.frontfx.model.state.ApplicationStateTool;
import perobobbot.frontfx.model.state.DashboardMainViewType;
import perobobbot.frontfx.model.view.PluggableController;
import perobobbot.lang.Nil;
import perobobbot.lang.SubscriptionHolder;

import java.util.HashMap;
import java.util.Map;

@FXMLController
@RequiredArgsConstructor
public class DashboardMenuController implements PluggableController {

    private final @NonNull FXApplicationIdentity applicationIdentity;
    private final @NonNull ActionExecutor actionExecutor;

    public ToggleGroup dashboardViewGroup;

    public ToggleButton usersButton;
    public ToggleButton credentialsButton;
    public ToggleButton botsButton;
    public ToggleButton extensionsButton;

    private final Map<DashboardMainViewType,ToggleButton> buttonByViewType = new HashMap<>();

    private final SubscriptionHolder subscriptionHolder = new SubscriptionHolder();

    public void initialize() {
        usersButton.managedProperty().bind(usersButton.visibleProperty());
        buttonByViewType.put(DashboardMainViewType.BOTS,botsButton);
        buttonByViewType.put(DashboardMainViewType.USERS,usersButton);
        buttonByViewType.put(DashboardMainViewType.CREDENTIALS,credentialsButton);
        buttonByViewType.put(DashboardMainViewType.EXTENSIONS,extensionsButton);
    }

    @Override
    public void onShowing() {
        subscriptionHolder.replaceWith(() -> applicationIdentity.addListenerAndCall(this::onApplicationStateChanged));
    }

    @Override
    public void onHiding() {
        subscriptionHolder.unsubscribe();
    }

    private void onApplicationStateChanged(@NonNull ApplicationStateTool tool) {
        final var viewType = tool.getDashboardState().getViewType();
        usersButton.setVisible(tool.canSeeUsers());
        dashboardViewGroup.selectToggle(buttonByViewType.get(viewType));
    }

    private void changeView(@NonNull DashboardMainViewType viewType) {
        actionExecutor.pushAction(ChangeDashboardMainView.class, viewType);
    }

    public void showExtensions(ActionEvent actionEvent) {
        this.changeView(DashboardMainViewType.EXTENSIONS);
    }

    public void showBots(ActionEvent actionEvent) {
        this.changeView(DashboardMainViewType.BOTS);
        this.actionExecutor.pushAction(RefreshBotList.class, Nil.NIL);
    }

    public void showCredentials(ActionEvent actionEvent) {
        this.changeView(DashboardMainViewType.CREDENTIALS);
    }

    public void showUsers(ActionEvent actionEvent) {
        this.changeView(DashboardMainViewType.USERS);
        this.actionExecutor.pushAction(RefreshUserList.class, Nil.NIL);
    }
}
