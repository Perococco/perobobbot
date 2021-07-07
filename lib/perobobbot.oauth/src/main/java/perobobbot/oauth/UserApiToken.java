package perobobbot.oauth;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.Secret;

import java.util.Optional;

@Value
public class UserApiToken implements ApiToken {
    @NonNull String userId;
    @NonNull String clientId;
    @NonNull Secret accessToken;
}
