package perobobbot.oauth;

import lombok.NonNull;

public interface OAuthTokenIdentifierSetter {

    void setTokenIdentifier(@NonNull TokenIdentifier tokenIdentifier);
}
