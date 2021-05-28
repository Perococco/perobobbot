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

    @NonNull ImmutableSet<Platform> getManagedPlatform();

    @NonNull Optional<OAuthController> findController(@NonNull Platform platform);

    default @NonNull OAuthController getController(@NonNull Platform platform) {
        return findController(platform).orElseThrow(() -> new OAuthUnmanagedPlatform(platform));
    }

    static OAuthManager create(@NonNull ImmutableList<OAuthController> controllers) {
        return new MapOAuthManager(controllers);
    }

    default @NonNull CompletionStage<UserIdentity> getUserIdentity(@NonNull DecryptedClient client, @NonNull Secret accessToken) {
        return getController(client.getPlatform()).getUserIdentity(client,accessToken);
    }

    default @NonNull UserOAuthInfo<Token> prepareUserOAuth(@NonNull DecryptedClient client, @NonNull ImmutableSet<? extends Scope> scopes) {
        return getController(client.getPlatform()).prepareUserOAuth(client,scopes);
    }




}
