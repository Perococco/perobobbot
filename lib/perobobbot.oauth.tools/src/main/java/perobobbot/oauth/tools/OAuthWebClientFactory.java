package perobobbot.oauth.tools;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import perobobbot.http.WebClientFactory;
import perobobbot.oauth.OAuthContextHolder;

@RequiredArgsConstructor
public class OAuthWebClientFactory implements WebClientFactory {

    private final @NonNull WebClient reference;


    @Override
    public @NonNull WebClient create() {
        final var builder = reference.mutate();

        OAuthContextHolder.getContext()
                          .getHeaderValues()
                          .forEach(builder::defaultHeader);

        return builder.build();

    }
}
