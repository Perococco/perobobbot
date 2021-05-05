package perobobbot.oauth;

public class OAuthRejected extends OAuthFailure {

    public OAuthRejected(String clientId) {
        super(clientId, "The user rejected the OAuth");
    }
}
