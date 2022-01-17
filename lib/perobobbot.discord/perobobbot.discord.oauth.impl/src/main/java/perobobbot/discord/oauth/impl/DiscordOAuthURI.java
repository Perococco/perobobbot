package perobobbot.discord.oauth.impl;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import org.springframework.web.util.UriComponentsBuilder;
import perobobbot.discord.oauth.api.DiscordScope;
import perobobbot.discord.oauth.api.Permission;
import perobobbot.lang.client.DecryptedClient;
import perobobbot.lang.Scope;
import perobobbot.lang.Secret;
import perobobbot.lang.SecretURI;
import perobobbot.oauth.OAuthUrlOptions;

import java.net.URI;

public class DiscordOAuthURI {

    //https://discord.com/api/oauth2/authorize	Base authorization URL
    //https://discord.com/api/oauth2/token	Token URL
    //https://discord.com/api/oauth2/token/revoke	Token Revocation URL


    private final @NonNull String apiEndpoint;

    public DiscordOAuthURI(int version) {
        this.apiEndpoint = "https://discord.com/api/v%d".formatted(version);
    }

    public @NonNull DiscordRequest getAppTokenURI(@NonNull DecryptedClient client) {

        return DiscordRequestBuilder.forClientCredentials()
                                    .setBaseUri(this.apiEndpoint + "/oauth2/token")
                                    .setClient(client)
                                    .setScopes(DiscordScope.IDENTIFY)
                                    .build();
    }

    public @NonNull DiscordRequest getRefreshURI(@NonNull DecryptedClient client, @NonNull Secret refreshToken) {
        return DiscordRequestBuilder.forRefreshToken()
                                    .setBaseUri(this.apiEndpoint + "/oauth2/token")
                                    .setClient(client)
                                    .setRefreshToken(refreshToken)
                                    .build();
    }


    public @NonNull DiscordRequest getUserInfoURI(@NonNull Secret accessToken) {
        return DiscordRequestBuilder.forUserInfo()
                                    .setBaseUri(this.apiEndpoint + "/oauth2/@me")
                                    .setAccessToken(accessToken)
                                    .build();

    }

    public @NonNull SecretURI getRevokeURI(@NonNull String clientId, @NonNull Secret accessToken) {

        return new SecretURI(UriComponentsBuilder.fromHttpUrl(apiEndpoint + "/oauth2/revoke")
                                                 .queryParam("client_id", clientId)
                                                 .queryParam("token", accessToken.getValue())
                                                 .build()
                                                 .toUri());
    }

    //TODO
    public @NonNull URI getValidateTokenURI() {
        return URI.create("https://id.twitch.tv/oauth2/validate");
    }


    public @NonNull DiscordRequest getUserTokenURI(@NonNull DecryptedClient client, @NonNull String code, @NonNull URI redirectURI) {
        return DiscordRequestBuilder.forUserToken()
                                    .setBaseUri(this.apiEndpoint + "/oauth2/token")
                                    .setClient(client)
                                    .setCode(code)
                                    .setRedirectUri(redirectURI)
                                    .build();
    }


    public @NonNull URI getUserAuthorizationURI(@NonNull String clientId,
                                                @NonNull ImmutableSet<? extends Scope> scopes,
                                                @NonNull String state,
                                                @NonNull URI oAuthRedirectURI,
                                                @NonNull OAuthUrlOptions options,
                                                @NonNull ImmutableSet<Permission> permissions) {
        final var builder = UriComponentsBuilder.fromHttpUrl(apiEndpoint + "/oauth2/authorize")
                                   .queryParam("response_type", "code")
                                   .queryParam("client_id", clientId)
                                   .queryParam("scope", Scope.scopeNamesSpaceSeparated(scopes))
                                   .queryParam("state", state)
                                   .queryParam("redirect_uri", oAuthRedirectURI)
                                   .queryParam("prompt", options.isForceVerify() ? "consent" : "none");

        if (!permissions.isEmpty()) {
            builder.queryParam("permissions",Permission.computeQueryParam(permissions));
        }


        return builder.build().toUri();
    }


}
