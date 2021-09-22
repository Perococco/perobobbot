package perobobbot.security.com;

import lombok.NonNull;
import lombok.Value;

import java.util.concurrent.CompletionStage;

@Value
public class OAuthData {

    /**
     * Information to continue the authorisation code flow
     */
    @NonNull OAuthInfo info;

    /**
     * A completion stage that will contain the token after the authorisation code flow completes
     */
    @NonNull CompletionStage<OAuthToken> authentication;

}
