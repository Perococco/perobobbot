package perobobbot.discord.gateway.impl;

import lombok.NonNull;
import perobobbot.discord.gateway.impl.message.SentGatewayEvent;

/**
 * Map message from or to the Discord Gateway
 */
public interface MessageMapper {

    @NonNull GatewayMessage<?> map(@NonNull String rawGatewayMessage);

    @NonNull String map(@NonNull SentGatewayEvent event);

    @NonNull String mapHeartbeat(Integer sequenceNumber);
}
