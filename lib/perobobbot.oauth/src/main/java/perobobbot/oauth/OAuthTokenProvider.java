package perobobbot.oauth;

import lombok.NonNull;

import java.util.Optional;

public interface OAuthTokenProvider {

    @NonNull Optional<Token> getUserToken();
    @NonNull Optional<Token> getClientToken();
}
