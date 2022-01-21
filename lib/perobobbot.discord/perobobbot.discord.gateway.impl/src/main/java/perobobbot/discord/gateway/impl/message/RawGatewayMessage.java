package perobobbot.discord.gateway.impl.message;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RawGatewayMessage {
    int op;
    /**
     * event data
     */
    JsonNode d;
    /**
     * sequence number, used for resuming sessions and heartbeats
     */
    Integer s;
    /**
     * the event name for this payload
     */
    String t;
}
