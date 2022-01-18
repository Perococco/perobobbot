package perobobbot.twitch.client.webclient.spring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import perobobbot.http.WebClientFactory;
import perobobbot.lang.Platform;
import perobobbot.oauth.ApiToken;
import perobobbot.oauth.OAuthWebClientFactory;

@RequiredArgsConstructor
public class TwitchOAuthWebClientFactory implements OAuthWebClientFactory {

    public static @NonNull TwitchOAuthWebClientFactory helix(@NonNull WebClientFactory webClientFactory) {
        final var factory = webClientFactory.mutate()
                                            .baseUrl("https://api.twitch.tv/helix")
                                            .addRateLimitLogger(Platform.TWITCH)
                                            .build();

        return new TwitchOAuthWebClientFactory(factory);

    }

    private final @NonNull WebClientFactory reference;

    @Override
    public @NonNull WebClientFactory create(@NonNull ApiToken apiToken) {
        return reference.mutate()
                        .addModifier(new TwitchOAuthHeaderSetter(apiToken))
                        .build();
    }
}
