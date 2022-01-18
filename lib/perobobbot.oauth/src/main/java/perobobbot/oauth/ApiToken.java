package perobobbot.oauth;

import lombok.NonNull;
import perobobbot.lang.Secret;

public sealed interface ApiToken permits ClientApiToken, UserApiToken, BotApiToken {

    @NonNull String getClientId();
    @NonNull Secret getAccessToken();
}
