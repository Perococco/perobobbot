package perococco.perobobbot.frontfx.gui.fxml;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.frontfx.model.FXApplicationIdentity;
import perobobbot.frontfx.model.state.ApplicationStateTool;
import perobobbot.frontfx.model.view.PluggableController;
import perobobbot.lang.Bot;
import perobobbot.lang.Credential;
import perobobbot.lang.Platform;
import perobobbot.lang.SubscriptionHolder;

@FXMLController
@RequiredArgsConstructor
public class BotListController implements PluggableController {

    private final @NonNull FXApplicationIdentity applicationIdentity;
    private final @NonNull SubscriptionHolder subscriptionHolder = new SubscriptionHolder();

    public TableView<Bot> botTable;
    public TableColumn<Bot,String> ownerColumn;
    public TableColumn<Bot, String> nameColumn;
    public TableColumn<Bot, String> credentialColumn;




    public void initialize() {
        ownerColumn.setCellValueFactory(cdf -> new SimpleObjectProperty<>(cdf.getValue().getOwnerLogin()));
        nameColumn.setCellValueFactory(cdf -> new SimpleObjectProperty<>(cdf.getValue().getName()));
        credentialColumn.setCellValueFactory(cdf -> new SimpleObjectProperty<>(cdf.getValue().findCredential(Platform.TWITCH).map(
                Credential::getNick).orElse("?")));
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
        this.botTable.getItems().setAll(tool.getBots());
    }


}
