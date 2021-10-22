package perobobbot.oauth.tools;

import lombok.NonNull;
import perobobbot.lang.Platform;
import perobobbot.lang.Scope;
import perobobbot.lang.token.DecryptedUserTokenView;

import java.util.Optional;

public interface BasicOAuthService {

    @NonNull Optional<DecryptedUserTokenView> findUserTokenView(@NonNull Platform platform);

    @NonNull Optional<DecryptedUserTokenView> findUserTokenView(@NonNull Platform platform, @NonNull Scope scope);
}
