package perobobbot.twitch.client.api.channelpoints;

import lombok.Value;

@Value
public class MaxPerStreamSetting {

    boolean enabled;
    int maxPerStream;

}
