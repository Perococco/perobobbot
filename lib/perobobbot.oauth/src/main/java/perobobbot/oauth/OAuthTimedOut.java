package perobobbot.oauth;

import lombok.NonNull;
import perobobbot.lang.Platform;

public class OAuthTimedOut extends OAuthFailure {

    public OAuthTimedOut(@NonNull Platform platform, @NonNull String clientId) {
        super(platform, clientId, "The OAuth process timed out");
    }
}
