package perobobbot.oauth;

import lombok.NonNull;
import perobobbot.lang.Platform;

public class OAuthRefreshFailed extends OAuthFailure {

    public OAuthRefreshFailed(@NonNull Platform platform, @NonNull String clientId, Throwable cause) {
        super(platform, clientId, cause);
    }
}
