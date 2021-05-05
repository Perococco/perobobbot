package perobobbot.oauth;

public class OAuthTimedOut extends OAuthFailure {

    public OAuthTimedOut(String clientId) {
        super(clientId, "The OAuth process timed out");
    }
}
