package perobobot.discord.gateway.api;

import lombok.NonNull;
import perobobbot.lang.Subscription;

public interface Gateway {

    @NonNull Subscription addGatewayEventListener(@NonNull GatewayEventListener listener);

}
