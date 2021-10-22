package perobobbot.oauth.tools._private;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.data.service.OAuthService;
import perobobbot.lang.Platform;
import perobobbot.lang.Scope;
import perobobbot.lang.token.DecryptedUserTokenView;
import perobobbot.oauth.tools.BasicOAuthService;

import java.util.Optional;

@RequiredArgsConstructor
public class BroadcasterBaseBasicOauthService implements BasicOAuthService {

    private final @NonNull OAuthService oAuthService;
    private final @NonNull String broadcasterId;

    @Override
    public @NonNull Optional<DecryptedUserTokenView> findUserTokenView(@NonNull Platform platform) {
        return oAuthService.findUserTokenByViewerId(broadcasterId,platform);
    }

    @Override
    public @NonNull Optional<DecryptedUserTokenView> findUserTokenView(@NonNull Platform platform, @NonNull Scope scope) {
        return oAuthService.findUserTokenByViewerId(broadcasterId,platform,scope);
    }
}
