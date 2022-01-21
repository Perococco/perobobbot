package perobobbot.discord.gateway.impl.state;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.discord.gateway.impl.GatewayState;

@RequiredArgsConstructor
public class DisconnectedGateway implements GatewayState {

    private final @NonNull StateData stateData;

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public GatewayState disconnect() {
        return this;
    }

    @Override
    public GatewayState connect() {
        return GatewayConnector.connect(stateData);
    }



}
