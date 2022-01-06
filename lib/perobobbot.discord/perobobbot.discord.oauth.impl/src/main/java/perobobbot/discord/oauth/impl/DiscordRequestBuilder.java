package perobobbot.discord.oauth.impl;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.discord.oauth.api.DiscordScope;
import perobobbot.discord.oauth.impl.builder.ClientCredentialsRequestBuilder;
import perobobbot.discord.oauth.impl.builder.RefreshDiscordRequestBuilder;
import perobobbot.discord.oauth.impl.builder.UserInfoRequestBuilder;
import perobobbot.discord.oauth.impl.builder.UserDiscordRequestBuilder;
import perobobbot.lang.DecryptedClient;

public interface DiscordRequestBuilder<B extends DiscordRequestBuilder<B>> {

    static @NonNull UserInfoRequestBuilder forUserInfo() {
        return new UserInfoRequestBuilder();
    }

    static @NonNull ClientCredentialsRequestBuilder forClientCredentials() {
        return new ClientCredentialsRequestBuilder();
    }

    static @NonNull RefreshDiscordRequestBuilder forRefreshToken() {
        return new RefreshDiscordRequestBuilder();
    }

    static @NonNull UserDiscordRequestBuilder forUserToken() {
        return new UserDiscordRequestBuilder();
    }

    @NonNull B setBaseUri(@NonNull String baseUri);
    @NonNull B setClient(@NonNull DecryptedClient client);
    @NonNull B setScopes(@NonNull ImmutableSet<DiscordScope> scopes);


    default @NonNull B setScopes(@NonNull DiscordScope... scopes) {
        return setScopes(ImmutableSet.copyOf(scopes));
    }

    @NonNull DiscordRequest build();




}
