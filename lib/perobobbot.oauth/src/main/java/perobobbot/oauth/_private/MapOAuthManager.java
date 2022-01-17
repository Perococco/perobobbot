package perobobbot.oauth._private;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.lang.client.DecryptedClient;
import perobobbot.lang.Platform;
import perobobbot.lang.Secret;
import perobobbot.lang.UserIdentity;
import perobobbot.oauth.*;

import java.util.concurrent.CompletionStage;
import java.util.function.Function;

public class MapOAuthManager implements OAuthManager {

    private final @NonNull ImmutableMap<Platform, OAuthController> controllerPerPlatform;


    public MapOAuthManager(@NonNull ImmutableList<OAuthController> controllers) {
        this.controllerPerPlatform = controllers.stream()
                                                .collect(ImmutableMap.toImmutableMap(OAuthController::getPlatform, Function.identity()));
    }

    @Override
    public @NonNull ImmutableSet<Platform> getManagedPlatform() {
        return controllerPerPlatform.keySet();
    }

    /**
     * @param platform a platform
     * @return the {@link OAuthController} for the requested platform
     * @throws OAuthUnmanagedPlatform if no controller exists for the requested platform
     */
    private @NonNull OAuthController getController(@NonNull Platform platform) {
        final var controller = this.controllerPerPlatform.get(platform);
        if (controller == null) {
            throw new OAuthUnmanagedPlatform(platform);
        }
        return controller;
    }


    public @NonNull CompletionStage<Token> getClientToken(@NonNull DecryptedClient decryptedClient) {
        return getController(decryptedClient.getPlatform()).getClientToken(decryptedClient);
    }


    /**
     * shortcut to <code>getController(client.getPlatform()).getUserIdentity(client,accessToken)</code>
     */
    public @NonNull CompletionStage<UserIdentity> getUserIdentity(@NonNull Platform platform, @NonNull Secret accessToken) {
        return getController(platform).getUserIdentity(accessToken);
    }

    public @NonNull CompletionStage<RefreshedToken> refreshToken(@NonNull DecryptedClient client, @NonNull Secret refreshToken) {
        return getController(client.getPlatform()).refreshToken(client, refreshToken);
    }

    public @NonNull CompletionStage<?> revokeToken(@NonNull DecryptedClient client, @NonNull Secret accessToken) {
        return getController(client.getPlatform()).revokeToken(client, accessToken);
    }

    @Override
    public @NonNull CompletionStage<?> validateToken(@NonNull Platform platform, @NonNull Secret accessToken) {
        return getController(platform).validateToken(accessToken);
    }

    /**
     * shortcut to <code>getController(client.getPlatform()).prepareUserOAuth(client,accessToken)</code>
     */
    public @NonNull UserOAuthInfo<Token> prepareUserOAuth(@NonNull DecryptedClient client, @NonNull OAuthUrlOptions options) {
        return getController(client.getPlatform()).prepareUserOAuth(client, options);
    }


}
