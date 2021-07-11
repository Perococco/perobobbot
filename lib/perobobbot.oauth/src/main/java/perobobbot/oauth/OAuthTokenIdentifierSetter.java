package perobobbot.oauth;

import lombok.NonNull;

public interface OAuthTokenIdentifierSetter {

    int VERSION = 1;

    void setTokenIdentifier(@NonNull TokenIdentifier tokenIdentifier);
}
