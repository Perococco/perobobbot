package perobbot.twitch.oauth;

import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.util.UriComponentsBuilder;
import perobobbot.lang.Scope;
import perobobbot.lang.Secret;
import perobobbot.lang.SecretURI;

import java.net.URI;

@RequiredArgsConstructor
public class TwitchOAuthURI {

    public @NonNull URI getValidateTokenURI() {
        return URI.create("https://id.twitch.tv/oauth2/validate");
    }

    public @NonNull SecretURI getRefreshURI(@NonNull String clientId, @NonNull Secret clientSecret, @NonNull String refreshToken) {
        return getTokenURIBuilder(clientId, clientSecret, GranType.REFRESH_TOKEN)
                .setRefreshToken(refreshToken)
                .build();
    }

    public @NonNull SecretURI getRevokeURI(@NonNull String clientId, @NonNull String accessToken) {
        return new SecretURI(UriComponentsBuilder.fromHttpUrl("https://id.twitch.tv/oauth2/revoke")
                                                 .queryParam("client_id", clientId)
                                                 .queryParam("token", accessToken)
                                                 .build()
                                                 .toUri());
    }

    public @NonNull SecretURI getUserTokenURI(@NonNull String clientId, @NonNull Secret clientSecret, @NonNull String code, @NonNull URI redirectURI) {
        return getTokenURIBuilder(clientId, clientSecret, GranType.AUTHORIZATION_CODE)
                .setCode(code)
                .setRedirectUri(redirectURI)
                .build();
    }

    public @NonNull SecretURI getAppTokenURI(@NonNull String clientId, @NonNull Secret clientSecret, @NonNull ImmutableSet<? extends Scope> scopes) {
        return getTokenURIBuilder(clientId, clientSecret, GranType.CLIENT_CREDENTIALS)
                .setScopes(scopes)
                .build();
    }

    public @NonNull URI getUserAuthorizationURI(@NonNull String clientId, @NonNull ImmutableSet<? extends Scope> scopes, @NonNull String state, @NonNull URI oAuthRedirectURI) {
        return UriComponentsBuilder.fromHttpUrl("https://id.twitch.tv/oauth2/authorize")
                                   .queryParam("client_id", clientId)
                                   .queryParam("redirect_uri", oAuthRedirectURI)
                                   .queryParam("response_type", "code")
                                   .queryParam("force_verify", "true")
                                   .queryParam("scope", Scope.scopeNamesSpaceSeparated(scopes))
                                   .queryParam("state", state)
                                   .build().toUri();
    }


    private @NonNull TokenURIBuilder getTokenURIBuilder(@NonNull String clientId, @NonNull Secret clientSecret, @NonNull GranType granType) {
        final var uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl("https://id.twitch.tv/oauth2/token")
                                                             .queryParam("client_id", clientId)
                                                             .queryParam("client_secret", clientSecret.getValue())
                                                             .queryParam("grant_type", granType.uriValue);
        return new TokenURIBuilder(uriComponentsBuilder);
    }

    @RequiredArgsConstructor
    private enum GranType {
        CLIENT_CREDENTIALS("client_credentials"),
        AUTHORIZATION_CODE("authorization_code"),
        REFRESH_TOKEN("refresh_token"),
        ;
        @Getter
        private final @NonNull String uriValue;
    }

    @RequiredArgsConstructor
    private static class TokenURIBuilder {
        private final @NonNull UriComponentsBuilder builder;

        public @NonNull SecretURI build() {
            return new SecretURI(builder.build().toUri());
        }

        public TokenURIBuilder setRedirectUri(@NonNull URI redirectUri) {
            builder.queryParam("redirect_uri", redirectUri);
            return this;
        }

        public TokenURIBuilder setCode(@NonNull String code) {
            builder.queryParam("code", code);
            return this;
        }

        public TokenURIBuilder setScopes(@NonNull ImmutableSet<? extends Scope> scopes) {
            builder.queryParam("scope", Scope.scopeNamesSpaceSeparated(scopes));
            return this;
        }

        public TokenURIBuilder setRefreshToken(@NonNull String refreshToken) {
            builder.queryParam("refresh_token", refreshToken);
            return this;
        }
    }


}
