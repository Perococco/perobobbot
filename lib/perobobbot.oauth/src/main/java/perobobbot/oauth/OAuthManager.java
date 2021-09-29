package perobobbot.oauth;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.lang.DecryptedClient;
import perobobbot.lang.Platform;
import perobobbot.lang.Scope;
import perobobbot.lang.Secret;
import perobobbot.oauth._private.MapOAuthManager;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

/**
 * Provide OAuth controller for a platform
 */
public interface OAuthManager {

    /**
     * @return all the managed platforms
     */
    @NonNull ImmutableSet<Platform> getManagedPlatform();

    /**
     * @param platform a platform
     * @return the {@link OAuthController} for the requested platform
     */
    @NonNull Optional<OAuthController> findController(@NonNull Platform platform);

    /**
     * @param platform a platform
     * @return the {@link OAuthController} for the requested platform
     * @throws OAuthUnmanagedPlatform if no controller exists for the requested platform
     */
    default @NonNull OAuthController getController(@NonNull Platform platform) {
        return findController(platform).orElseThrow(() -> new OAuthUnmanagedPlatform(platform));
    }

    /**
     * shortcut to <code>getController(client.getPlatform()).getUserIdentity(client,accessToken)</code>
     */
    default @NonNull CompletionStage<UserIdentity> getUserIdentity(@NonNull DecryptedClient client, @NonNull Secret accessToken) {
        return getController(client.getPlatform()).getUserIdentity(client,accessToken);
    }

    default @NonNull CompletionStage<RefreshedToken> refreshToken(@NonNull DecryptedClient client, @NonNull Secret refreshToken) {
        return getController(client.getPlatform()).refreshToken(client,refreshToken);
    }

    /**
     * shortcut to <code>getController(client.getPlatform()).prepareUserOAuth(client,accessToken)</code>
     */
    default @NonNull UserOAuthInfo<Token> prepareUserOAuth(@NonNull DecryptedClient client, @NonNull OAuthUrlOptions options) {
        return getController(client.getPlatform()).prepareUserOAuth(client,options);
    }


    /**
     * @param controllers the controllers to manage
     * @return a OAuthManager that will managed the provided controllers
     */
    static @NonNull OAuthManager create(@NonNull ImmutableList<OAuthController> controllers) {
        return new MapOAuthManager(controllers);
    }


    void dispose();


}
