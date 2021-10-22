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
public class LoginBaseBasicOauthService implements BasicOAuthService {

    private final @NonNull OAuthService oAuthService;
    private final @NonNull String login;

    @Override
    public @NonNull Optional<DecryptedUserTokenView> findUserTokenView(@NonNull Platform platform) {
        return oAuthService.findUserMainToken(login,platform);
    }

    @Override
    public @NonNull Optional<DecryptedUserTokenView> findUserTokenView(@NonNull Platform platform, @NonNull Scope scope) {
        return oAuthService.findUserMainToken(login,platform,scope);
    }
}
