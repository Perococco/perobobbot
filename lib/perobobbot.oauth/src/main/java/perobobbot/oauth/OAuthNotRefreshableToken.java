package perobobbot.oauth;

import lombok.NonNull;

public class OAuthNotRefreshableToken extends OAuthFailure {

    public OAuthNotRefreshableToken(String clientId, String reason) {
        super(clientId, reason);
    }

    public OAuthNotRefreshableToken(@NonNull String clientId) {
        super(clientId, "The provided token is not refreshable. Are you trying to refresh an App Token");
    }
}
