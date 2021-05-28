package perobobbot.oauth;

import lombok.NonNull;
import perobobbot.lang.token.DecryptedClientToken;
import perobobbot.lang.token.DecryptedUserToken;
import perobobbot.lang.token.Token;

import java.util.Optional;
import java.util.stream.Stream;

public class OAuthContext {

    private DecryptedUserToken userToken;
    private DecryptedClientToken clientToken;

    private TokenIdentifier identifier = null;


    public void clear() {
        this.clearUserToken();
        this.clearClientToken();
        this.clearIdentifier();
    }

    public @NonNull Optional<DecryptedUserToken> getUserToken() {
        return Optional.ofNullable(userToken);
    }

    public @NonNull Optional<DecryptedClientToken> getClientToken() {
        return Optional.ofNullable(clientToken);
    }

    public @NonNull Optional<TokenIdentifier> getTokenIdentifier() {
        return Optional.ofNullable(identifier);
    }

    public void setUserToken(@NonNull DecryptedUserToken userToken) {
        this.userToken = userToken;
    }

    public void setClientToken(@NonNull DecryptedClientToken clientToken) {
        this.clientToken = clientToken;
    }

    public void setTokenIdentifier(@NonNull TokenIdentifier tokenIdentifier) {
        this.identifier = tokenIdentifier;
    }

    public void clearUserToken() {
        this.userToken = null;
    }

    public void clearClientToken() {
        this.clientToken = null;
    }

    public void clearIdentifier() {
        this.identifier = null;
    }


    public @NonNull Optional<String> getHeaderValue() {
        return Stream.of(this.clientToken, this.userToken)
                     .map(Token::getAccessToken)
                     .findFirst()
                     .map(t -> "Bearer " + t);

    }
}
