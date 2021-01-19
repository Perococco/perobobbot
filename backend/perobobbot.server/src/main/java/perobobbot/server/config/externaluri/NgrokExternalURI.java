package perobobbot.server.config.externaluri;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import perobobbot.lang.Todo;

import java.net.URI;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;

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
                                 .block(Duration.ofSeconds(30));

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
