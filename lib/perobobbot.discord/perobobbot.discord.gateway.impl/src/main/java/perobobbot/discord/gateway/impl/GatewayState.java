package perobobbot.discord.gateway.impl;

import lombok.NonNull;

public interface GatewayState {

    boolean isConnected();

    @NonNull GatewayState connect();

    @NonNull GatewayState disconnect();

}
