package perobobbot.twitch.client.webclient.spring;

import org.springframework.web.reactive.function.client.ClientRequest;
import perobobbot.oauth.OAuthContextHolder;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class OAuthHeaderInitializer implements Function<ClientRequest, Mono<ClientRequest>> {
    @Override
    public Mono<ClientRequest> apply(ClientRequest clientRequest) {
        final var newRequest = ClientRequest.from(clientRequest);

        OAuthContextHolder.getContext()
                          .getHeaderValues()
                          .forEach(newRequest::header);

        return Mono.just(newRequest.build());

    }

}
