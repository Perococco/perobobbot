package perobobbot.discord.oauth.impl.builder;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.net.URI;

public class UserDiscordRequestBuilder extends DiscordRequestBuilderBase<UserDiscordRequestBuilder> {

    private String code;
    private URI redirectUri;

    public @NonNull UserDiscordRequestBuilder setCode(@NonNull String code) {
        this.code = code;
        return this;
    }

    public @NonNull UserDiscordRequestBuilder setRedirectUri(@NonNull URI redirectUri) {
        this.redirectUri = redirectUri;
        return this;
    }


    @Override
    protected UserDiscordRequestBuilder getThis() {
        return this;
    }

    @Override
    protected void completeHeaders(HttpHeaders headers) {
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    }

    @Override
    protected @NonNull ImmutableMap<String, String> getExtraData() {
        final var client = getClient();
        return ImmutableMap.<String, String>builder()
                           .put("client_id", client.getClientId())
                           .put("client_secret", client.getClientSecret().getValue())
                           .put("grant_type", "authorization_code")
                           .put("code", this.code)
                           .put("redirect_uri", this.redirectUri.toString())
                           .build();
    }

}
