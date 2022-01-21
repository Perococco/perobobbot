package perobobbot.discord.gateway.impl.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ConnectionProperties {

    @JsonProperty("$os")
    @NonNull String os;//	your operating system

    @JsonProperty("$browser")
    @NonNull String browser;//your library name

    @JsonProperty("$device")
    @NonNull String device;//your library name

}
