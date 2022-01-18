package perobobbot.discord.client.webclient.spring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.http.WebClientFactory;
import perobobbot.lang.Platform;
import perobobbot.oauth.ApiToken;
import perobobbot.oauth.OAuthWebClientFactory;

@RequiredArgsConstructor
public class DiscordOAuthWebClientFactory implements OAuthWebClientFactory {

    public static @NonNull DiscordOAuthWebClientFactory lastVersion(@NonNull WebClientFactory webClientFactory) {
        return withVersion(webClientFactory,9);

    }

    public static @NonNull DiscordOAuthWebClientFactory withVersion(@NonNull WebClientFactory webClientFactory, int version) {
        final var factory = webClientFactory.mutate()
                                            .baseUrl("https://discord.com/api/v%s".formatted(version))
                                            .addRateLimitLogger(Platform.DISCORD)
                                            .build();

        return new DiscordOAuthWebClientFactory(factory);

    }

    private final @NonNull WebClientFactory reference;

    @Override
    public @NonNull WebClientFactory create(@NonNull ApiToken apiToken) {
        return reference.mutate()
                        .addModifier(new DiscordOAuthHeaderSetter(apiToken))
                        .build();
    }
}
