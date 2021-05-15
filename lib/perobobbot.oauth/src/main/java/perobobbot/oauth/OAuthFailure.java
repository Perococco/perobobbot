package perobobbot.oauth;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.lang.PerobobbotException;

public class OAuthFailure extends PerobobbotException {

    @Getter
    private final @NonNull String clientId;

    public OAuthFailure(String clientId, String reason) {
        super(formMessage(clientId,reason));
        this.clientId  = clientId;
    }

    public OAuthFailure(String clientId, Throwable cause) {
        super(formMessage(clientId,cause.getMessage()),cause);
        this.clientId = clientId;
    }

    private static String formMessage(@NonNull String clientId, @NonNull String reason) {
        return "The OAuth process failed for client '" + clientId + "' : " + reason;
    }
}
