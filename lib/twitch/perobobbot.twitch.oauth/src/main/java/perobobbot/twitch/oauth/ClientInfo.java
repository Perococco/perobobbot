package perobobbot.twitch.oauth;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.Secret;

@Value
public class ClientInfo {

    @NonNull String id;

    @NonNull Secret secret;
}
