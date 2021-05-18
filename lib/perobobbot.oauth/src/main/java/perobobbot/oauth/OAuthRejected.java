package perobobbot.oauth;

import lombok.NonNull;
import perobobbot.lang.Platform;

public class OAuthRejected extends OAuthFailure {

    public OAuthRejected(@NonNull Platform platform, @NonNull String clientId) {
        super(platform, clientId, "The user rejected the OAuth");
    }
}
