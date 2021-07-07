package perobobbot.oauth.tools;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.data.service.OAuthService;
import perobobbot.lang.Platform;
import perobobbot.lang.Scope;
import perobobbot.lang.fp.Function1;
import perobobbot.lang.token.DecryptedUserTokenView;
import perobobbot.oauth.UserOAuth;

import java.util.Optional;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class UserTokenViewGetter {

    public static Optional<DecryptedUserTokenView> getToken(
            @NonNull OAuthService oAuthService,
            @NonNull Platform platform,
            @NonNull String login,
            @NonNull UserOAuth scopeRequirements) {
        return new UserTokenViewGetter(oAuthService, platform, login, scopeRequirements).getToken();
    }

    public static Function1<String,Optional<DecryptedUserTokenView>> createTokenGetter(
            @NonNull OAuthService oAuthService,
            @NonNull Platform platform,
            @NonNull UserOAuth scopeRequirements) {
        return login -> getToken(oAuthService, platform, login, scopeRequirements);
    }

    private final @NonNull OAuthService oAuthService;
    private final @NonNull Platform platform;
    private final @NonNull String login;
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
                token = oAuthService.findUserMainToken(login, platform, Scope.basic(scope)).orElse(null);
            }
        }
    }

    private void retrieveWithoutScopeIfNecessary() {
        if (token == null && scopeRequirements.optional()) {
            token = oAuthService.findUserMainToken(login, platform).orElse(null);
        }
    }

}
