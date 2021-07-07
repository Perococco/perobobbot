package perobobbot.twitch.client.api.channelpoints;

import lombok.Value;

@Value
public class GlobalCooldownSetting {
    boolean enabled;
    int globalCooldownSeconds;
}
