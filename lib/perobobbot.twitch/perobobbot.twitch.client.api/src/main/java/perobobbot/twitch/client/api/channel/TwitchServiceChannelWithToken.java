package perobobbot.twitch.client.api.channel;

import lombok.NonNull;
import perobobbot.oauth.ClientApiToken;
import reactor.core.publisher.Mono;

public interface TwitchServiceChannelWithToken {

    @NonNull Mono<ChannelInformation> getChannelInformation(@NonNull ClientApiToken token, @NonNull String broadcasterId);

}
