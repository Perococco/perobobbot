package perobobbot.oauth;

import lombok.NonNull;

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


    public @NonNull Optional<TokenIdentifier> getTokenIdentifier() {
        return Optional.ofNullable(tokenIdentifier);
    }

    @Override
    public void setTokenIdentifier(@NonNull TokenIdentifier tokenIdentifier) {
        this.tokenIdentifier = tokenIdentifier;
    }



}
