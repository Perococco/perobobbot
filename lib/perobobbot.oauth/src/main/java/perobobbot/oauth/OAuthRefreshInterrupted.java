package perobobbot.oauth;

import lombok.NonNull;
import perobobbot.lang.Platform;

public class OAuthRefreshInterrupted extends OAuthFailure {

    public OAuthRefreshInterrupted(@NonNull Platform platform, @NonNull String clientId, Throwable cause) {
        super(platform, clientId, cause);
    }
}
