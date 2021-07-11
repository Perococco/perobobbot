package perobobbot.twitch.client.api.channel;

import lombok.NonNull;
import perobobbot.oauth.ClientOAuth;
import reactor.core.publisher.Mono;

public interface TwitchServiceChannel {

    @ClientOAuth
    @NonNull Mono<ChannelInformation> getChannelInformation(@NonNull String broadcasterId);
}
