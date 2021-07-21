package perobobbot.twitch.client.api.channelpoints;


import com.fasterxml.jackson.annotation.JsonProperty;
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

    String prompt;
    @JsonProperty("is_enabled")
    Boolean enabled;//true
    @JsonSerialize(using = ColorSerializer.class)
    Color backgroundColor;
    @JsonProperty("is_user_input_required")
    Boolean userInputRequired;//false
    @JsonProperty("is_max_per_stream_enabled")
    Boolean maxPerStreamEnabled; //false
    Integer maxPerStream;

    @JsonProperty("is_max_per_user_per_stream_enabled")
    Boolean maxPerUserPerStreamEnabled; //false
    Integer maxPerUserPerStream;

    @JsonProperty("is_global_cooldown_enabled")
    Boolean globalCooldownEnabled;
    Integer globalCooldownSeconds;

    Boolean shouldRedemptionsSkipRequestQueue;


}
