package perobobbot.twitch.client.webclient;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import perobobbot.http.WebClientFactory;
import perobobbot.oauth.OAuthWebClientFactory;
import perobobbot.twitch.client.api.TwitchServiceWithToken;
import perobobbot.twitch.client.api.channel.TwitchServiceChannelWithToken;
import perobobbot.twitch.client.api.channelpoints.TwitchServiceChannelPointsWithToken;
import perobobbot.twitch.client.api.evensub.TwitchServiceEventSubWithToken;
import perobobbot.twitch.client.api.games.TwitchServiceGamesWithToken;
import perobobbot.twitch.client.webclient.channel.WebClientTwitchServiceChannel;
import perobobbot.twitch.client.webclient.channelpoints.WebClientTwitchServiceChannelPoints;
import perobobbot.twitch.client.webclient.eventsub.WebClientTwitchServiceEventSub;
import perobobbot.twitch.client.webclient.games.WebClientTwitchServiceGames;
import perobobbot.twitch.client.webclient.spring.TwitchOAuthWebClientFactory;

@RequiredArgsConstructor
public class ProxyTwitchService implements TwitchServiceWithToken {

    public static @NonNull TwitchServiceWithToken helix(@NonNull WebClientFactory webClientFactory) {
        return new ProxyTwitchService(TwitchOAuthWebClientFactory.helix(webClientFactory));
    }

    @Delegate
    private final @NonNull TwitchServiceGamesWithToken games;
    @Delegate
    private final @NonNull TwitchServiceChannelWithToken channel;
    @Delegate
    private final @NonNull TwitchServiceEventSubWithToken eventSubs;
    @Delegate
    private final @NonNull TwitchServiceChannelPointsWithToken channelPoints;

    public ProxyTwitchService(@NonNull OAuthWebClientFactory oAuthWebClientFactory) {
        this(
                new WebClientTwitchServiceGames(oAuthWebClientFactory),
                new WebClientTwitchServiceChannel(oAuthWebClientFactory),
                new WebClientTwitchServiceEventSub(oAuthWebClientFactory),
                new WebClientTwitchServiceChannelPoints(oAuthWebClientFactory)
        );
    }
}
