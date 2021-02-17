package perococco.perobobbot.frontfx.gui.fxml;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.frontfx.model.FXApplicationIdentity;
import perobobbot.frontfx.model.state.ApplicationStateTool;
import perobobbot.frontfx.model.view.PluggableController;
import perobobbot.lang.SubscriptionHolder;
import perobobbot.security.com.SimpleUser;

@FXMLController
@RequiredArgsConstructor
public class UserListController implements PluggableController {

    private final @NonNull FXApplicationIdentity applicationIdentity;

    public TableView<SimpleUser> userTable;
    public TableColumn<SimpleUser,String> loginColumn;
    public TableColumn<SimpleUser,String> rolesColumn;
    public TableColumn<SimpleUser,SimpleUser> deactivatedColumn;

    private final SubscriptionHolder subscriptionHolder = new SubscriptionHolder();

    public void initialize() {
        userTable.setEditable(true);
        loginColumn.setCellValueFactory(cd -> new SimpleObjectProperty<>(cd.getValue().getLogin()));
        rolesColumn.setCellValueFactory(cd -> new SimpleObjectProperty<>(cd.getValue().getRolesAsString()));
        deactivatedColumn.setCellValueFactory(cd -> new SimpleObjectProperty<>(cd.getValue()));

        deactivatedColumn.setCellFactory(c -> new DeactivationCell());
    }

    @Override
    public void onShowing() {
        subscriptionHolder.replaceWith(() -> applicationIdentity.addListener(this::onApplicationStateChange));
    }

    @Override
    public void onHiding() {
        subscriptionHolder.unsubscribe();
    }

    private void onApplicationStateChange(@NonNull ApplicationStateTool tool) {
        userTable.getItems().setAll(tool.getUsers());
    }


}
