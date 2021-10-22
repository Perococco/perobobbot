package perobobbot.oauth;

import lombok.NonNull;
import perobobbot.lang.fp.Function0;

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


    @Override
    public <T> @NonNull T wrapCall(@NonNull TokenIdentifier tokenIdentifier, @NonNull Function0<T> call) {
        final TokenIdentifier current = this.tokenIdentifier;
        try {
            this.tokenIdentifier = tokenIdentifier;
            return call.f();
        } finally {
            this.tokenIdentifier = current;
        }
    }
}
