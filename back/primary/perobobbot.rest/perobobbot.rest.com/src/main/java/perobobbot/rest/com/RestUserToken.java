package perobobbot.rest.com;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.TypeScript;
import perobobbot.lang.ViewerIdentity;
import perobobbot.lang.token.DecryptedUserTokenView;

import java.time.Instant;
import java.util.UUID;

@Value
@Builder
@TypeScript
public class RestUserToken {

    @NonNull UUID id;
    @NonNull String ownerLogin;
    @NonNull ViewerIdentity viewerIdentity;
    @NonNull Instant expirationInstant;

    public static @NonNull RestUserToken fromUserTokenView(@NonNull DecryptedUserTokenView decryptedUserTokenView) {
        return new RestUserToken(
                decryptedUserTokenView.getId(),
                decryptedUserTokenView.getOwnerLogin(),
                decryptedUserTokenView.getViewerIdentity(),
                decryptedUserTokenView.getExpirationInstant());
    }

}
