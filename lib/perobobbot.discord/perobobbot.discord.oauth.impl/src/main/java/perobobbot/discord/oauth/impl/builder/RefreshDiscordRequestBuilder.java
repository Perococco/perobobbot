package perobobbot.discord.oauth.impl.builder;

import com.google.common.collect.ImmutableMap;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import perobobbot.lang.Secret;

public class RefreshDiscordRequestBuilder extends DiscordRequestBuilderBase<RefreshDiscordRequestBuilder> {

    private Secret refreshToken;

    @Override
    protected ImmutableMap<String, String> getExtraData() {
        final var client = getClient();
        return ImmutableMap.of(
                "client_id", client.getClientId(),
                "client_secret", client.getClientSecret().getValue(),
                "grant_type", "refresh_token",
                "refresh_token", refreshToken.getValue());
    }

    public RefreshDiscordRequestBuilder setRefreshToken(Secret refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    @Override
    protected void completeHeaders(HttpHeaders headers) {
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    }

    @Override
    protected RefreshDiscordRequestBuilder getThis() {
        return this;
    }
}
