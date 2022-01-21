package perobobbot.discord.gateway.impl;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.NonNull;
import lombok.Value;
import perobobbot.discord.gateway.impl.message.OpCode;

@Value
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GatewayMessage<T> {
    @NonNull OpCode opCode;
    /**
     * event data
     */
    T event;
    /**
     * sequence number, used for resuming sessions and heartbeats
     */
    Integer sequenceNumber;
    /**
     * the event name for this payload
     */
    String eventName;
}
