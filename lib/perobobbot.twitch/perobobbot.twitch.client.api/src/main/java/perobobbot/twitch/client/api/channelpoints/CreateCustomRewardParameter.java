package perobobbot.twitch.client.api.channelpoints;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.SuperBuilder;

import java.awt.*;

@Value
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateCustomRewardParameter extends CustomRewardParameterBase{

    @NonNull String title;
    int cost;

}
