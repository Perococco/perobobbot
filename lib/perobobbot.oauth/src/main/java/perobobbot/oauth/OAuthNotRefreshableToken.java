package perobobbot.oauth;

import lombok.NonNull;
import perobobbot.lang.Platform;

public class OAuthNotRefreshableToken extends OAuthFailure {

    public OAuthNotRefreshableToken(@NonNull Platform platform, @NonNull String clientId, @NonNull String reason) {
        super(platform, clientId, reason);
    }

    public OAuthNotRefreshableToken(@NonNull Platform platform, @NonNull String clientId) {
        super(platform, clientId, "The provided token is not refreshable. Are you trying to refresh an App Token");
    }
}
