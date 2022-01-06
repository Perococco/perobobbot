package perobobbot.discord.oauth.impl.builder;

import com.google.common.collect.ImmutableMap;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class ClientCredentialsRequestBuilder extends DiscordRequestBuilderBase<ClientCredentialsRequestBuilder> {

    @Override
    protected ImmutableMap<String, String> getExtraData() {
        return ImmutableMap.of("grant_type","client_credentials");
    }

    @Override
    protected void completeHeaders(HttpHeaders headers) {
        final var client = getClient();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(client.getClientId(), client.getClientSecret().getValue());
    }

    @Override
    protected ClientCredentialsRequestBuilder getThis() {
        return this;
    }
}
