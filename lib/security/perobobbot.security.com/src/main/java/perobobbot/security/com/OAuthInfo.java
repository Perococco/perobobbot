package perobobbot.security.com;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.TypeScript;

import java.net.URI;
import java.util.UUID;

@Value
@TypeScript
public class OAuthInfo {

    @NonNull URI oauthURI;
    @NonNull UUID signInId;

}
