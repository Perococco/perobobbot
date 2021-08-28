package perobobbot.twitch.client.api.channelpoints;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.SuperBuilder;

@Value
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateCustomRewardParameter extends CustomRewardParameterBase{

    @NonNull String title;
    int cost;

}
