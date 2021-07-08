package perobobbot.oauth;

import lombok.NonNull;
import perobobbot.http.WebClientFactory;

public interface OAuthWebClientFactory {

    @NonNull WebClientFactory create(@NonNull ApiToken apiToken);

}
