package perobobbot.discord.resources;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.NonNull;
import lombok.Value;

@Value
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GatewayBot {

    @NonNull String url;// The WSS URL that can be used for connecting to the gateway
    int shards;// The recommended number of shards to use when connecting
    @NonNull SessionStartLimit sessionStartLimit; // session_start_limit -> sessionStartLimit

}