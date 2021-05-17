package perobobbot.oauth;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.fp.Function1;

import java.util.Optional;

@RequiredArgsConstructor
public enum TokenType {
    CLIENT_TOKEN(OAuthTokenProvider::getClientToken),
    USER_TOKEN(OAuthTokenProvider::getUserToken),
    ;

    private final @NonNull Function1<? super OAuthTokenProvider, ? extends Optional<Token>> tokenGetter;

    public @NonNull Optional<Token> getToken(@NonNull OAuthTokenProvider provider) {
        return tokenGetter.apply(provider);
    }
}
