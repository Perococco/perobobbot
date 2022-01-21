package perobobot.discord.gateway.api;

import lombok.NonNull;
import perobobbot.discord.resources.GatewayEvent;

public interface GatewayEventListener {

    void onGatewayEvent(@NonNull GatewayEvent event);
}
