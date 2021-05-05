package perobobbot.oauth;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.lang.Platform;
import perobobbot.oauth._private.MapOAuthManager;

import java.util.Optional;

/**
 * Provide OAuth controller for a platform
 */
public interface OAuthManager {

    @NonNull Optional<OAuthController> getController(@NonNull Platform platform);

    static OAuthManager create(@NonNull ImmutableList<OAuthController> controllers) {
        return MapOAuthManager.create(controllers);
    }
}
