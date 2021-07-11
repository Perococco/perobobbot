package perococco.perobobbot.http;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.reactive.function.client.*;
import perobobbot.http.WebClientFactory;
import perobobbot.lang.fp.Consumer1;
import reactor.core.publisher.Mono;

@Log4j2
@RequiredArgsConstructor
public class WebClientFactoryWithModifiers implements WebClientFactory {

    private final @NonNull WebClient reference;

    private final @NonNull ImmutableList<Consumer1<? super WebClient.Builder>> modifiers;

    @Override
    public @NonNull WebClient create() {
        final var builder = reference.mutate();
        builder.filter(ExchangeFilterFunction.ofRequestProcessor(this::logRequest));
        builder.filter(ExchangeFilterFunction.ofResponseProcessor(this::logResponse));
        modifiers.forEach(m -> m.accept(builder));
        return builder.build();
    }


    @Override
    public @NonNull WebClientFactory.Builder mutate() {
        return new WebClientFactoryBuilder(reference, modifiers);
    }

    private Mono<ClientResponse> logResponse(ClientResponse response) {
        if (response.statusCode() != null && (response.statusCode().is4xxClientError() || response.statusCode().is5xxServerError())) {
            return response.bodyToMono(String.class)
                           .flatMap(body -> {
                               LOG.debug("Body is {}", body);
                               return Mono.just(response);
                           });
        } else {
            return Mono.just(response);
        }
    }

    private Mono<ClientRequest> logRequest(ClientRequest clientRequest) {
        return Mono.just(clientRequest);
    }

}
