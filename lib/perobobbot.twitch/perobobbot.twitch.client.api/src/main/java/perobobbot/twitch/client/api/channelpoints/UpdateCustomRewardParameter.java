package perobobbot.twitch.client.api.channelpoints;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import lombok.experimental.SuperBuilder;

@Value
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateCustomRewardParameter extends CustomRewardParameterBase {

    String title;
    Integer cost;
    @JsonProperty("is_paused")
    Boolean paused;

}
