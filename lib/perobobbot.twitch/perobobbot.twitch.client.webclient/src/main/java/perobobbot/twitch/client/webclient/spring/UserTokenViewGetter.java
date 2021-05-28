package perobobbot.twitch.client.webclient.spring;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.data.service.OAuthService;
import perobobbot.lang.Platform;
import perobobbot.lang.Scope;
import perobobbot.lang.fp.Function1;
import perobobbot.lang.token.DecryptedUserTokenView;

import java.util.Optional;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class UserTokenViewGetter {

    public static Optional<DecryptedUserTokenView> getToken(
            @NonNull OAuthService oAuthService,
            @NonNull Platform platform,
            @NonNull String login,
            @NonNull CallRequirements callRequirements) {
        return new UserTokenViewGetter(oAuthService, platform, login, callRequirements).getToken();
    }

    public static Function1<String,Optional<DecryptedUserTokenView>> createTokenGetter(
            @NonNull OAuthService oAuthService,
            @NonNull Platform platform,
            @NonNull CallRequirements callRequirements) {
        return login -> getToken(oAuthService, platform, login,callRequirements);
    }

    private final @NonNull OAuthService oAuthService;
    private final @NonNull Platform platform;
    private final @NonNull String login;
    private final @NonNull CallRequirements callRequirements;

    private DecryptedUserTokenView token = null;

    private @NonNull Optional<DecryptedUserTokenView> getToken() {
        retrieveWithScopeIfNecessary();
        retrieveWithoutScopeIfNecessary();
        return Optional.ofNullable(token);
    }

    private void retrieveWithScopeIfNecessary() {
        if (token == null) {
            token = callRequirements.getScope()
                                    .flatMap(scope -> oAuthService.findUserToken(login, platform, scope))
                                    .orElse(null);
        }
    }

    private void retrieveWithoutScopeIfNecessary() {
        if (token == null && callRequirements.hasOptionalOrNoScope()) {
            token = oAuthService.findUserToken(login, platform).orElse(null);
        }
    }

}
