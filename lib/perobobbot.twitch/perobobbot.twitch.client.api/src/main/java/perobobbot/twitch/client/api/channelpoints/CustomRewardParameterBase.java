package perobobbot.twitch.client.api.channelpoints;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.experimental.SuperBuilder;
import perobobbot.twitch.api.deser.ColorSerializer;

import java.awt.*;

@Value
@NonFinal
@SuperBuilder
public class CustomRewardParameterBase {

    @JsonAlias("is_enabled")
    Boolean enabled;//true
    @JsonSerialize(using = ColorSerializer.class)
    Color backgroundColor;
    @JsonAlias("is_user_input_required")
    Boolean userInputRequired;//false
    @JsonAlias("is_max_per_stream_enabled")
    Boolean maxPerStreamEnabled; //false
    Integer maxPerStream;

    @JsonAlias("is_max_per_user_per_stream_enabled")
    Boolean maxPerUserPerStreamEnabled; //false
    Integer maxPerUserPerStream;

    @JsonAlias("is_global_cooldown_enabled")
    Boolean globalCooldownEnabled;
    Integer globalCooldownSeconds;

    Boolean shouldRedemptionsSkipRequestQueue;


}
