package perobobbot.oauth;

import lombok.NonNull;
import org.springframework.web.reactive.function.client.WebClient;
import perobobbot.http.WebClientFactory;

public interface OAuthWebClientFactory {

    @NonNull WebClientFactory create(@NonNull ApiToken apiToken);

}
