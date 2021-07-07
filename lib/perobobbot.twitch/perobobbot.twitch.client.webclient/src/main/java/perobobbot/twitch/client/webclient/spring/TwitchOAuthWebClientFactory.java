package perobobbot.twitch.client.webclient.spring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.http.WebClientFactory;
import perobobbot.oauth.ApiToken;
import perobobbot.oauth.OAuthWebClientFactory;

@RequiredArgsConstructor
public class TwitchOAuthWebClientFactory implements OAuthWebClientFactory {

    private final @NonNull WebClientFactory reference;

    @Override
    public @NonNull WebClientFactory create(@NonNull ApiToken apiToken) {
        return reference.mutate()
                        .addModifier(new TwitchOAuthHeaderSetter(apiToken))
                        .build();
    }
}
