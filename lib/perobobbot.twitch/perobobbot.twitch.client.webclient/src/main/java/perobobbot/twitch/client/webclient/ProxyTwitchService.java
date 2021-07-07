package perobobbot.twitch.client.webclient;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import perobobbot.oauth.OAuthWebClientFactory;
import perobobbot.twitch.client.api.TwitchServiceWithToken;
import perobobbot.twitch.client.api.evensub.TwitchServiceEventSubWithToken;
import perobobbot.twitch.client.api.games.TwitchServiceGamesWithToken;
import perobobbot.twitch.client.webclient.eventsub.WebClientTwitchServiceEventSub;
import perobobbot.twitch.client.webclient.games.WebClientTwitchServiceGames;

@RequiredArgsConstructor
public class ProxyTwitchService implements TwitchServiceWithToken {

    @Delegate
    private final @NonNull TwitchServiceGamesWithToken games;
    @Delegate
    private final @NonNull TwitchServiceEventSubWithToken eventSubs;

    public ProxyTwitchService(@NonNull OAuthWebClientFactory oAuthWebClientFactory) {
        this(
                new WebClientTwitchServiceGames(oAuthWebClientFactory),
                new WebClientTwitchServiceEventSub(oAuthWebClientFactory)
        );
    }
}
