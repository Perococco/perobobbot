package perobobbot.oauth;

import lombok.NonNull;
import perobobbot.lang.Secret;

public sealed interface ApiToken permits ClientApiToken, UserApiToken{

    @NonNull String getClientId();

    @NonNull Secret getAccessToken();
}
