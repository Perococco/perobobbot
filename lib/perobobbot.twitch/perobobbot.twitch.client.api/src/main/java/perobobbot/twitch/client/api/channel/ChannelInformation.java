package perobobbot.twitch.client.api.channel;

import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.UserInfo;
import perobobbot.twitch.client.api.TwitchApiPayload;

@Value
public class ChannelInformation implements TwitchApiPayload {

    @NonNull UserInfo broadcaster;
    @NonNull String broadcasterLanguage;
    @NonNull String gameId;
    @NonNull String gameName;
    @NonNull String title;
    int delay;
}
