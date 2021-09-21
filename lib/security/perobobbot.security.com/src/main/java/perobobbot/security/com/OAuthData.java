package perobobbot.security.com;

import lombok.NonNull;
import lombok.Value;

import java.util.concurrent.CompletionStage;

@Value
public class OAuthData {

    @NonNull OAuthInfo info;
    @NonNull CompletionStage<OAuthToken> authentication;

}
