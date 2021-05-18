package perobbot.twitch.oauth.spring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import perobbot.twitch.oauth.TwitchOAuthController;
import perobobbot.lang.Client;
import perobobbot.lang.Instants;
import perobobbot.lang.Platform;
import perobobbot.oauth.OAuthController;
import perobobbot.oauth.OAuthSubscriptions;

@RequiredArgsConstructor
public class TwitchOAuthControllerFactory implements OAuthController.Factory {

    private final @NonNull OAuthSubscriptions oAuthSubscriptions;
    private final @NonNull Instants instants;

    @Override
    public @NonNull Platform getPlatform() {
        return Platform.TWITCH;
    }

    @Override
    public @NonNull OAuthController createOAuthController(@NonNull Client client) {
        return new TwitchOAuthController(oAuthSubscriptions, WebClient.create(), instants, client);
    }
}
