package perobobbot.oauth;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.client.DecryptedClient;
import perobobbot.lang.Platform;
import perobobbot.lang.Secret;
import perobobbot.lang.UserIdentity;

import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
public class OAuthControllerFromManager implements OAuthController {

    private final @NonNull OAuthManager manager;
    private final @NonNull Platform platform;

    @Override
    public @NonNull Platform getPlatform() {
        return platform;
    }

    @Override
    public @NonNull UserOAuthInfo<Token> prepareUserOAuth(@NonNull DecryptedClient client, @NonNull OAuthUrlOptions options) {
        return manager.prepareUserOAuth(client,options);
    }

    @Override
    public @NonNull CompletionStage<Token> getClientToken(@NonNull DecryptedClient client) {
        return manager.getClientToken(client);
    }

    @Override
    public @NonNull CompletionStage<?> revokeToken(@NonNull DecryptedClient client, @NonNull Secret accessToken) {
        return manager.revokeToken(client,accessToken);
    }

    @Override
    public @NonNull CompletionStage<RefreshedToken> refreshToken(@NonNull DecryptedClient client, @NonNull Secret tokenToRefresh) {
        return manager.refreshToken(client,tokenToRefresh);
    }

    @Override
    public @NonNull CompletionStage<?> validateToken(@NonNull Secret accessToken) {
        return manager.validateToken(platform, accessToken);
    }

    @Override
    public @NonNull CompletionStage<UserIdentity> getUserIdentity(@NonNull Secret accessToken) {
        return manager.getUserIdentity(platform,accessToken);
    }
}
