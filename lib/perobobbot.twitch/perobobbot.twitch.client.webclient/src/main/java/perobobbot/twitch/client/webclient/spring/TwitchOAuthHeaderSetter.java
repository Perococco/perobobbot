package perobobbot.twitch.client.webclient.spring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;
import perobobbot.lang.fp.Consumer1;
import perobobbot.oauth.ApiToken;

@RequiredArgsConstructor
public class TwitchOAuthHeaderSetter implements Consumer1<WebClient.Builder> {

    private final @NonNull ApiToken token;

    @Override
    public void f(WebClient.@NonNull Builder builder) {
        builder.defaultHeader("client-id",token.getClientId());
        builder.defaultHeader(HttpHeaders.AUTHORIZATION,"Bearer "+token.getAccessToken().getValue());
    }
}
