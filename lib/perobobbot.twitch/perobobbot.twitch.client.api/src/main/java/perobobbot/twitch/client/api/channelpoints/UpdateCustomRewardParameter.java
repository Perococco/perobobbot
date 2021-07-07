package perobobbot.twitch.client.api.channelpoints;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.SuperBuilder;

import java.awt.*;

@Value
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateCustomRewardParameter extends CustomRewardParameterBase {

    String title;
    Integer cost;

}
