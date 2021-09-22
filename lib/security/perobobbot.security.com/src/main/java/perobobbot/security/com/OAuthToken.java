package perobobbot.security.com;

import lombok.NonNull;
import perobobbot.lang.Platform;
import perobobbot.oauth.Token;

/**
 * @param token the token returned by the platform on successful authentication
 * @param platform the platform against which the authentication was performed
 * @param userId the id on the platform of the authenticated user
 * @param jwtInfo the JWT  of the authenticated user to
 */
public record OAuthToken(@NonNull Token token,
                         @NonNull Platform platform,
                         @NonNull String userId,
                         @NonNull JwtInfo jwtInfo) {

    public @NonNull String getUserLogin() {
        return jwtInfo.getUser().getLogin();
    }
}
