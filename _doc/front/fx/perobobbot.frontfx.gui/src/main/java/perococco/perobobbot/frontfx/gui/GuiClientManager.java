package perococco.perobobbot.frontfx.gui;

import lombok.NonNull;
import lombok.experimental.Delegate;
import org.springframework.stereotype.Component;
import perobobbot.frontfx.model.ApplicationIdentity;
import perobobbot.frontfx.model.state.ApplicationState;
import perobobbot.rest.client.ClientManager;
import perobobbot.rest.client.ClientManagerFactory;

@Component
public class GuiClientManager implements ClientManager {

    private final @NonNull ClientManagerFactory clientManagerFactory;

    private final @NonNull ApplicationIdentity applicationIdentity;

    @Delegate
    private @NonNull ClientManager delegate;

    public GuiClientManager(@NonNull ApplicationIdentity applicationIdentity) {
        this.applicationIdentity = applicationIdentity;
        this.clientManagerFactory = ClientManagerFactory.getInstance();

        final var tool = new BasicApplicationStateTool(applicationIdentity.getState());
        this.delegate = this.clientManagerFactory.create(tool.getServerBaseURL());

        this.applicationIdentity.addListener(this::onApplicationStateChange);
    }

    private void onApplicationStateChange(ApplicationState oldState, ApplicationState newState) {
        final var oldUrl = new BasicApplicationStateTool(oldState).getServerBaseURL();
        final var newUrl = new BasicApplicationStateTool(newState).getServerBaseURL();
        if (oldUrl.equals(newUrl)) {
            return;
        }

        this.delegate = clientManagerFactory.create(newUrl);
    }

}
