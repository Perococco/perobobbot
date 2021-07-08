package perobobbot.lang;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.token.DecryptedUserToken;

import java.util.UUID;

@Value
public class DecryptedTokenInfo {
    @NonNull UUID tokenId;
    @NonNull DecryptedUserToken decryptedUserToken;

    public @NonNull Secret getAccessToken() {
        return decryptedUserToken.getAccessToken();
    }
}
