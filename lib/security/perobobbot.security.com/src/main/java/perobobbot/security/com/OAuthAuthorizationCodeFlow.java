package perobobbot.security.com;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.lang.Platform;

import java.util.UUID;

public interface OAuthAuthorizationCodeFlow {

    int VERSION = 1;

    @NonNull OAuthData oauthWith(@NonNull Platform openIdPlatform);

    @NonNull OAuthData oauthWith(@NonNull Platform openIdPlatform, @NonNull ImmutableSet<String> scopes);

    @NonNull OAuthData oauthWith(@NonNull Platform openIdPlatform, @NonNull String... scopes);

    @NonNull JwtInfo getOpenIdUser(@NonNull UUID id) throws Throwable;
}
