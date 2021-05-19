package perobobbot.oauth;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.lang.Platform;
import perobobbot.oauth._private.MapOAuthManager;

import java.util.Optional;

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
}
