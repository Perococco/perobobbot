package perobobbot.discord.resources;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Value;

@Value
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RoleTag {

    String botId;//the id of the bot this role belongs to
    String integrationId;//	snowflake	the id of the integration this role belongs to
    Object premiumSubscriber;//	whether this is the guild's premium subscriber role
}
