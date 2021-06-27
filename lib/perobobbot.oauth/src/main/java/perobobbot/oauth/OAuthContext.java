package perobobbot.oauth;

import lombok.NonNull;
import org.springframework.http.HttpHeaders;
import perobobbot.lang.PerobobbotException;
import perobobbot.lang.SafeClient;
import perobobbot.lang.TokenType;
import perobobbot.lang.token.DecryptedClientTokenView;
import perobobbot.lang.token.DecryptedTokenView;
import perobobbot.lang.token.DecryptedUserTokenView;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Contains the information to retrieve the OAuth token
 * required by some protected calls
 */
public class OAuthContext implements OAuthTokenIdentifierSetter {

    /**
     * Information obtained from entry points (Rest API, of chat bot)
     * to retrieve the appropriate token
     */
    private TokenIdentifier tokenIdentifier = null;

    /**
     * obtained on protected called services and contains information about
     * the type of required token and scopes.
     */
    private ScopeRequirements scopeRequirements = null;

    /**
     * The user token retrieved from the DB based on {@link #tokenIdentifier} and {@link #scopeRequirements}
     */
    private DecryptedUserTokenView userToken = null;

    /**
     * The client token retrieved from the DB based on {@link #tokenIdentifier} and {@link #scopeRequirements}
     */
    private DecryptedClientTokenView clientToken = null;

    private SafeClient client = null;


    public @NonNull Optional<TokenIdentifier> getTokenIdentifier() {
        return Optional.ofNullable(tokenIdentifier);
    }

    public void setTokenIdentifier(@NonNull TokenIdentifier tokenIdentifier) {
        this.tokenIdentifier = tokenIdentifier;
    }

    public void setScopeRequirements(@NonNull ScopeRequirements scopeRequirements) {
        this.scopeRequirements = scopeRequirements;
    }

    public @NonNull ScopeRequirements getCallRequirements() {
        return Optional.ofNullable(this.scopeRequirements)
                       .orElseThrow(() -> new PerobobbotException(
                               "CallRequirements not set, did you initialize the OAuth context before calling this method ?"));
    }

    public @NonNull TokenType getTokenType() {
        return getCallRequirements().getTokenType();
    }


    public @NonNull Map<String, String> getHeaderValues() {
        final Map<String, String> headers = new HashMap<>();
        final var tokenView = getTokenView().orElse(null);

        if (client != null) {
            headers.put("client-id", client.getClientId());
        }

        if (tokenView != null) {
            headers.put(HttpHeaders.AUTHORIZATION, "Bearer " + tokenView.getAccessToken());
        }

        return headers;
    }

    private @NonNull Optional<DecryptedTokenView> getTokenView() {
        final var tokenType = getTokenType();
        return switch (tokenType) {
            case CLIENT_TOKEN -> Optional.ofNullable(clientToken);
            case USER_TOKEN -> Optional.ofNullable(userToken);
        };
    }

    public @NonNull Optional<DecryptedUserTokenView> getUserToken() {
        return Optional.ofNullable(userToken);
    }
    public @NonNull Optional<DecryptedClientTokenView> getClientToken() {
        return Optional.ofNullable(clientToken);
    }

    public void setClientToken(@NonNull DecryptedClientTokenView clientToken) {
        this.clientToken = clientToken;
    }

    public void setUserToken(@NonNull DecryptedUserTokenView userToken) {
        this.userToken = userToken;
    }

    public void setClient(@NonNull SafeClient client) {
        this.client = client;
    }
}
