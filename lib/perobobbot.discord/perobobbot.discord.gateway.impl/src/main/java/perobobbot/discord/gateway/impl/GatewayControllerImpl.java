package perobobbot.discord.gateway.impl;

import lombok.NonNull;
import perobobbot.discord.gateway.impl.state.DisconnectedGateway;
import perobobbot.discord.gateway.impl.state.StateData;
import perobobbot.discord.resources.GatewayBot;
import perobobbot.lang.Identity;
import perobobbot.lang.Secret;
import perobobbot.lang.Subscription;
import perobobot.discord.gateway.api.GatewayController;
import perobobot.discord.gateway.api.GatewayEventListener;

//TODO Handle rate limits (2 commands per second max)
//TODO Handle resuming

public class GatewayControllerImpl implements GatewayController {

    private final Identity<GatewayState> gatewayIdentity;

    private final GatewayListenerDispatcher listenerDispatcher = new GatewayListenerDispatcher();

    public GatewayControllerImpl(@NonNull GatewayBot gatewayBot,
                                 int version,
                                 @NonNull Secret botToken,
                                 @NonNull MessageMapper messageMapper
    ) {
        final var stateData = StateData.builder()
                                       .gatewayBot(gatewayBot)
                                       .version(version)
                                       .listener(listenerDispatcher)
                                       .botToken(botToken)
                                       .messageMapper(messageMapper)
                                       .build();
        this.gatewayIdentity = Identity.create(new DisconnectedGateway(stateData));
    }

    @Override
    public @NonNull Subscription addGatewayEventListener(@NonNull GatewayEventListener listener) {
        return listenerDispatcher.addListener(listener);
    }

    @Override
    public boolean isConnected() {
        return gatewayIdentity.get(GatewayState::isConnected);
    }

    @Override
    public void connect() {
        gatewayIdentity.mutate(GatewayState::connect);
    }

    @Override
    public void disconnect() {
        gatewayIdentity.mutate(GatewayState::disconnect);
    }
}
