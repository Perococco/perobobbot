package perobobbot.twitch.client.api.channelpoints;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Value;
import lombok.experimental.SuperBuilder;

@Value
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateCustomRewardParameter extends CustomRewardParameterBase {

    String title;
    Integer cost;

}
