package perobobbot.data.com;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.Secret;
import perobobbot.oauth.Token;

import java.util.UUID;

@Value
public class DataToken {

    @NonNull String ownerLogin;
    @NonNull UUID clientId;
    @NonNull Token token;

    public @NonNull Secret getAccessToken() {
        return token.getAccessToken();
    }
}
