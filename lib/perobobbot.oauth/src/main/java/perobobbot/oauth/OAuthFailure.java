package perobobbot.oauth;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.lang.PerobobbotException;
import perobobbot.lang.Platform;

public class OAuthFailure extends PerobobbotException {

    @Getter
    private final @NonNull Platform platform;

    @Getter
    private final @NonNull String clientId;

    public OAuthFailure(@NonNull Platform platform, @NonNull String clientId, String reason) {
        super(formMessage(clientId,reason));
        this.platform = platform;
        this.clientId  = clientId;
    }

    public OAuthFailure(@NonNull Platform platform, @NonNull String clientId, Throwable cause) {
        super(formMessage(clientId,cause.getMessage()),cause);
        this.platform = platform;
        this.clientId = clientId;
    }

    private static String formMessage(@NonNull String clientId, @NonNull String reason) {
        return "The OAuth process failed for client '" + clientId + "' : " + reason;
    }
}
