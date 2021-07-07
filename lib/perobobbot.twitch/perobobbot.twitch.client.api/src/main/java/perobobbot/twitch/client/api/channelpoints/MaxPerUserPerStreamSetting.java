package perobobbot.twitch.client.api.channelpoints;

import lombok.Value;

@Value
public class MaxPerUserPerStreamSetting {

    boolean enabled;
    int maxPerUserPerStream;

}
