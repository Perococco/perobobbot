package perobobbot.oauth._private;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.Platform;
import perobobbot.oauth.OAuthController;
import perobobbot.oauth.OAuthManager;

import java.util.Optional;
import java.util.function.Function;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MapOAuthManager implements OAuthManager {

    public static @NonNull OAuthManager create(@NonNull ImmutableList<OAuthController> controllers) {
        return new MapOAuthManager(
                controllers.stream()
                           .collect(ImmutableMap.toImmutableMap(OAuthController::getPlatform, Function.identity()))
        );
    }

    private final @NonNull ImmutableMap<Platform, OAuthController> controllerPerPlatform;

    @Override
    public @NonNull Optional<OAuthController> getController(@NonNull Platform platform) {
        return Optional.ofNullable(controllerPerPlatform.get(platform));
    }
}
