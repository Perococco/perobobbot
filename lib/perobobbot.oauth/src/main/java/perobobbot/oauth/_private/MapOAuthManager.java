package perobobbot.oauth._private;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.lang.Platform;
import perobobbot.oauth.OAuthController;
import perobobbot.oauth.OAuthManager;

import java.util.Optional;
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

    @Override
    public @NonNull Optional<OAuthController> findController(@NonNull Platform platform) {
        return Optional.ofNullable(controllerPerPlatform.get(platform));
    }

    @Override
    public void dispose() {
        controllerPerPlatform.forEach((p,o) -> o.dispose());
    }
}
