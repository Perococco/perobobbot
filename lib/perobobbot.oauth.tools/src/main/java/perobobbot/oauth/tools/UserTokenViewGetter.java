package perobobbot.oauth.tools;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.Platform;
import perobobbot.lang.Scope;
import perobobbot.lang.fp.Function1;
import perobobbot.lang.token.DecryptedUserTokenView;
import perobobbot.oauth.UserOAuth;

import java.util.Optional;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class UserTokenViewGetter {

    public static Optional<DecryptedUserTokenView> getToken(
            @NonNull BasicOAuthService oAuthService,
            @NonNull Platform platform,
            @NonNull UserOAuth scopeRequirements) {
        return new UserTokenViewGetter(oAuthService, platform, scopeRequirements).getToken();
    }

    public static Function1<BasicOAuthService,Optional<DecryptedUserTokenView>> createTokenGetter(
            @NonNull Platform platform,
            @NonNull UserOAuth scopeRequirements) {
        return oAuthService -> getToken(oAuthService, platform, scopeRequirements);
    }

    private final @NonNull BasicOAuthService oAuthService;
    private final @NonNull Platform platform;
    private final @NonNull UserOAuth scopeRequirements;

    private DecryptedUserTokenView token = null;

    private @NonNull Optional<DecryptedUserTokenView> getToken() {
        retrieveWithScopeIfNecessary();
        retrieveWithoutScopeIfNecessary();
        return Optional.ofNullable(token);
    }

    private void retrieveWithScopeIfNecessary() {
        if (token == null) {
            final var scope = scopeRequirements.scope();
            if (!scope.isEmpty()) {
                token = oAuthService.findUserTokenView(platform, Scope.basic(scope)).orElse(null);
            }
        }
    }

    private void retrieveWithoutScopeIfNecessary() {
        if (token == null && scopeRequirements.optional()) {
            token = oAuthService.findUserTokenView(platform).orElse(null);
        }
    }

}
