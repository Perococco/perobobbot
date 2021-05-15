package perobobbot.oauth;

import lombok.NonNull;

import java.util.concurrent.CompletionStage;

public interface OAuthTokenRefresher {

    @NonNull CompletionStage<?> refreshToken(@NonNull Token expiredToken);

}
