package perobobbot.twitch.client.webclient.channel;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.oauth.ClientApiToken;
import perobobbot.oauth.OAuthWebClientFactory;
import perobobbot.twitch.client.api.channel.ChannelInformation;
import perobobbot.twitch.client.api.channel.TwitchServiceChannelWithToken;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class WebClientTwitchServiceChannel implements TwitchServiceChannelWithToken {

    private final @NonNull OAuthWebClientFactory webClientFactory;

    @Override
    public @NonNull Mono<ChannelInformation> getChannelInformation(@NonNull ClientApiToken token, @NonNull String broadcasterId) {
        return webClientFactory.create(token)
                               .get("/channels", "broadcaster_id", broadcasterId)
                               .retrieve()
                               .bodyToMono(GetChannelInformationResponse.class)
                               .map(r -> r.getData()[0]);
    }


}
