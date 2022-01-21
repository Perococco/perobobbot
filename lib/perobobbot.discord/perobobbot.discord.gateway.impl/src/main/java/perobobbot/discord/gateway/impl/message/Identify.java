package perobobbot.discord.gateway.impl.message;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Identify implements SentGatewayEvent {

    @NonNull String token;
    @NonNull ConnectionProperties properties;//	connection properties	-
    Boolean compress;//	whether this connection supports compression of packets	false
    Integer largeThreshold;//value between 50 and 250, total number of members where the gateway will stop sending offline members in the guild member list	50
    int[] shard;//array of two integers (shard_id, num_shards)	used for Guild Sharding	-
    Object presence;//presence structure for initial presence information	-
    int intents;//	the Gateway Intents you wish to receive

    @Override
    public @NonNull OpCode getOpCode() {
        return OpCode.Identify;
    }
}
