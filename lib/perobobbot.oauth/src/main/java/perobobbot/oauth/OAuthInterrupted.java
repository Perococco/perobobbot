package perobobbot.oauth;

import lombok.NonNull;
import perobobbot.lang.Platform;

public class OAuthInterrupted extends OAuthFailure {

    public OAuthInterrupted(@NonNull Platform platform, @NonNull String clientId) {
        super(platform, clientId, "The OAuth process has been interrupted");
    }
}
