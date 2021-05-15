package perobobbot.twitch.client.api;

import lombok.NonNull;
import perobobbot.oauth.OAuthTokenProvider;

public interface TwitchServiceFactory {

    @NonNull TwitchService create(@NonNull OAuthTokenProvider oAuthTokenProvider);

}
