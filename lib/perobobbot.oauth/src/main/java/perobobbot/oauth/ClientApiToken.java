package perobobbot.oauth;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.Secret;


@Value
public class ClientApiToken implements ApiToken {

    @NonNull String clientId;
    @NonNull Secret accessToken;
}
