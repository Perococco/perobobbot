package perobobbot.discord.resources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.NonNull;
import lombok.Value;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Ready implements GatewayEvent {

    int v;//gateway version
    DiscordUser user;//information about the user including email
    UnavailableGuild[] guilds;//	the guilds the user is in
    @NonNull String session_id;//	used for resuming connections
    int[] shard;//rray of two integers (shard_id, num_shards)	the shard information associated with this session, if sent when identifying
    @NonNull Application application;//	partial application object	contains id and flags
}
