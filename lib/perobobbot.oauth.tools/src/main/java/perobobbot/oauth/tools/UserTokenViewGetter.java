package perobobbot.oauth.tools;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.data.service.OAuthService;
import perobobbot.lang.Platform;
import perobobbot.lang.fp.Function1;
import perobobbot.lang.token.DecryptedUserTokenView;
import perobobbot.oauth.ScopeRequirements;

import java.util.Optional;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class UserTokenViewGetter {

    public static Optional<DecryptedUserTokenView> getToken(
            @NonNull OAuthService oAuthService,
            @NonNull Platform platform,
            @NonNull String login,
            @NonNull ScopeRequirements scopeRequirements) {
        return new UserTokenViewGetter(oAuthService, platform, login, scopeRequirements).getToken();
    }

    public static Function1<String,Optional<DecryptedUserTokenView>> createTokenGetter(
            @NonNull OAuthService oAuthService,
            @NonNull Platform platform,
            @NonNull ScopeRequirements scopeRequirements) {
        return login -> getToken(oAuthService, platform, login, scopeRequirements);
    }

    private final @NonNull OAuthService oAuthService;
    private final @NonNull Platform platform;
    private final @NonNull String login;
    private final @NonNull ScopeRequirements scopeRequirements;

    private DecryptedUserTokenView token = null;

    private @NonNull Optional<DecryptedUserTokenView> getToken() {
        retrieveWithScopeIfNecessary();
        retrieveWithoutScopeIfNecessary();
        return Optional.ofNullable(token);
    }

    private void retrieveWithScopeIfNecessary() {
        if (token == null) {
            token = scopeRequirements.getScope()
                                     .flatMap(scope -> oAuthService.findUserMainToken(login, platform, scope))
                                     .orElse(null);
        }
    }

    private void retrieveWithoutScopeIfNecessary() {
        if (token == null && scopeRequirements.hasOptionalOrNoScope()) {
            token = oAuthService.findUserMainToken(login, platform).orElse(null);
        }
    }

}
