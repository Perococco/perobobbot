package perobobbot.discord.oauth.impl.builder;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import org.springframework.http.HttpHeaders;
import perobobbot.lang.Secret;

public class UserInfoRequestBuilder extends DiscordRequestBuilderBase<UserInfoRequestBuilder> {

    private Secret accessToken;

    public UserInfoRequestBuilder setAccessToken(Secret accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @Override
    protected UserInfoRequestBuilder getThis() {
        return this;
    }

    @Override
    protected void completeHeaders(HttpHeaders headers) {
        headers.setBearerAuth(accessToken.getValue());
    }

    @Override
    protected @NonNull ImmutableMap<String, String> getExtraData() {
        return ImmutableMap.of();
    }

}
