package perobobbot.server.config.externaluri;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.time.Duration;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
public class NgrokExternalURI implements ExternalURI {

    public static final String PUBLIC_URL_KEY = "public_url";

    private final @NonNull String ngrokTunnelName;

    @Override
    public @NonNull URI getURI() {
        final var map = WebClient.create()
                                 .get()
                                 .uri(getNgrokURI())
                                 .retrieve()
                                 .bodyToMono(Map.class)
                                 .doOnError(t -> LOG.warn("Fail to get ngrok public url. Is 'ngrok' launched ? err = '{}'",t.getMessage()))
                                 .block(Duration.ofSeconds(10));

        final var publicUrl = map.get(PUBLIC_URL_KEY);

        if (publicUrl == null) {
            throw new IllegalStateException("Could not get the public URL from the ngrok configuration. URL used is '"+getNgrokURI()+"'");
        }

        return URI.create(publicUrl.toString());
    }

    public @NonNull String getNgrokURI() {
        return "localhost:4040/api/tunnels/"+ngrokTunnelName;
    }
}
