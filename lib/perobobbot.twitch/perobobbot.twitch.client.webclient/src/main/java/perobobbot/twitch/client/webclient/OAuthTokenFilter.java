package perobobbot.twitch.client.webclient;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import perobobbot.oauth.OAuthTokenProvider;
import perobobbot.oauth.Token;
import perobobbot.lang.TokenType;
import perobobbot.twitch.client.api.TokenTypeProvider;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RequiredArgsConstructor
public class OAuthTokenFilter implements ExchangeFilterFunction {

    private final @NonNull TokenTypeProvider tokenTypeProvider;
    private final @NonNull OAuthTokenProvider oauthTokenProvider;

    @Override
    public Mono<ClientResponse> filter(ClientRequest request, ExchangeFunction next) {
        final var tokenType = tokenTypeProvider.getTokenType();
        final var token = getToken(tokenType);
        token.map(Token::getAccessToken)
             .ifPresent(accessToken -> request.headers().add("Authorization", "Bearer " + accessToken));
        return next.exchange(request);
    }

    private @NonNull Optional<Token> getToken(@NonNull TokenType tokenType) {
        return switch (tokenType) {
            case CLIENT_TOKEN -> oauthTokenProvider.getClientToken();
            case USER_TOKEN -> oauthTokenProvider.getUserToken();
        };
    }
}
