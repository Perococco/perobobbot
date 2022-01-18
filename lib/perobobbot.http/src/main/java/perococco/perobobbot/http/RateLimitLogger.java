package perococco.perobobbot.http;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.reactive.function.client.ClientResponse;
import perobobbot.lang.CastTool;
import perobobbot.lang.Platform;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.function.Function;

@Log4j2
@RequiredArgsConstructor
public class RateLimitLogger implements Function<ClientResponse, Mono<ClientResponse>> {

    public static @NonNull Optional<RateLimitLogger> forPlatform(@NonNull Platform platform) {
        final var header = switch (platform) {
            case DISCORD -> "X-RateLimit-Remaining";
            case TWITCH -> "Ratelimit-Remaining";
            default -> null;
        };

        return Optional.ofNullable(header).map(h -> new RateLimitLogger(platform,h));
    }


    private final @NonNull Platform platform;

    private final @NonNull String remainingHeader;

    @Override
    public Mono<ClientResponse> apply(ClientResponse response) {
        final var remaining = response.headers()
                                      .header(remainingHeader)
                                      .stream()
                                      .map(CastTool::castToInt)
                                      .flatMap(Optional::stream)
                                      .mapToInt(Integer::intValue)
                                      .min();

        if (remaining.isPresent() && remaining.getAsInt() < 10) {
            LOG.warn("Only {} requests remaining for {}", remaining,platform);
        }

        return Mono.just(response);

    }

}
