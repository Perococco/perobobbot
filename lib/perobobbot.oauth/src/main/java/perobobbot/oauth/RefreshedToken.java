package perobobbot.oauth;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.Secret;

@Value
public class RefreshedToken {

    @NonNull Secret accessToken;
    Secret refreshToken;
}
