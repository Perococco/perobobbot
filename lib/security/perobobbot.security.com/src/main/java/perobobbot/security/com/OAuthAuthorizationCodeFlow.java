package perobobbot.security.com;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.lang.Platform;

import java.util.UUID;

public interface OAuthAuthorizationCodeFlow {

    int VERSION = 1;

    /**
     * Start an authorization code flow for the given platform with the default scopes
     * @param openIdPlatform the platform to perform the authorization against
     * @return data require to proceed the authorization code flow (like the URL to call, and the id of the process)
     */
    @NonNull OAuthData oauthWith(@NonNull Platform openIdPlatform);

    /**
     * Start an authorization code flow for the given platform with the provided scopes (keeping
     * only those that are in the default scopes)
     * @param openIdPlatform the platform to perform the authorization against
     * @return data require to proceed the authorization code flow (like the URL to call)
     */
    @NonNull OAuthData oauthWith(@NonNull Platform openIdPlatform, @NonNull ImmutableSet<String> scopes);

    /**
     * Return the jwt token obtained from the user oauth token. This is basically the same as retrieving
     * the result from the {@link OAuthData#getAuthentication()} completion stage.
     * @param id the id of the authorization code flow process
     * @return the token obtained when the user accept the authorization
     * @throws Throwable if any error occurs
     */
    @NonNull JwtInfo getOpenIdUser(@NonNull UUID id) throws Throwable;
}
