package perobobbot.discord.resources;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Value;

@Value
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SessionStartLimit {
    int total;//The total number of session starts the current user is allowed
    int remaining;//The remaining number of session starts the current user is allowed
    int resetAfter;//	The number of milliseconds after which the limit resets
    int maxConcurrency;//	The number of identify requests allowed per 5 seconds
}
