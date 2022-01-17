package perobobbot.oauth;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.lang.client.DecryptedClient;
import perobobbot.lang.Platform;
import perobobbot.lang.Secret;
import perobobbot.lang.UserIdentity;
import perobobbot.oauth._private.MapOAuthManager;

import java.util.concurrent.CompletionStage;

/**
 * Provide OAuth controller for a platform
 */
public interface OAuthManager {

    /**
     * @return all the managed platforms
     */
    @NonNull ImmutableSet<Platform> getManagedPlatform();

    @NonNull CompletionStage<Token> getClientToken(@NonNull DecryptedClient decryptedClient);

    /**
     * shortcut to <code>getController(client.getPlatform()).getUserIdentity(client,accessToken)</code>
     */
    @NonNull CompletionStage<UserIdentity> getUserIdentity(@NonNull Platform platform, @NonNull Secret accessToken);

    @NonNull CompletionStage<RefreshedToken> refreshToken(@NonNull DecryptedClient client, @NonNull Secret refreshToken);

    @NonNull CompletionStage<?> revokeToken(@NonNull DecryptedClient client, @NonNull Secret accessToken);

    @NonNull UserOAuthInfo<Token> prepareUserOAuth(@NonNull DecryptedClient client, @NonNull OAuthUrlOptions options);

    @NonNull CompletionStage<?> validateToken(@NonNull Platform platform, @NonNull Secret accessToken);

    /**
     * @param controllers the controllers to manage
     * @return a OAuthManager that will managed the provided controllers
     */
    static @NonNull OAuthManager create(@NonNull ImmutableList<OAuthController> controllers) {
        return new MapOAuthManager(controllers);
    }


}
