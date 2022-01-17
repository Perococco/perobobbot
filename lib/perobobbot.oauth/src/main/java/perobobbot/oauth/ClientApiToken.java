package perobobbot.oauth;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.client.DecryptedClient;
import perobobbot.lang.Secret;


@Value
public class ClientApiToken implements ApiToken {

    @NonNull String clientId;
    @NonNull Secret accessToken;

    public static @NonNull ClientApiToken fromClient(@NonNull DecryptedClient decryptedClient) {
        return new ClientApiToken(decryptedClient.getClientId(),decryptedClient.getClientSecret());
    }
}
