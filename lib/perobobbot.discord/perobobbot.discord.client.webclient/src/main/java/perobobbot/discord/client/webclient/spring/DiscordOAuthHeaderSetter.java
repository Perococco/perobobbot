package perobobbot.discord.client.webclient.spring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;
import perobobbot.lang.fp.Consumer1;
import perobobbot.oauth.ApiToken;
import perobobbot.oauth.BotApiToken;
import perobobbot.oauth.ClientApiToken;
import perobobbot.oauth.UserApiToken;

@RequiredArgsConstructor
public class DiscordOAuthHeaderSetter implements Consumer1<WebClient.Builder> {

    private final @NonNull ApiToken apiToken;

    @Override
    public void f(WebClient.@NonNull Builder builder) {
        if (apiToken instanceof BotApiToken) {
            builder.defaultHeader(HttpHeaders.AUTHORIZATION, "Bot " + apiToken.getAccessToken().getValue());
        } else {
            builder.defaultHeader("client-id", apiToken.getClientId());
            builder.defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiToken.getAccessToken().getValue());
        }

    }
}
