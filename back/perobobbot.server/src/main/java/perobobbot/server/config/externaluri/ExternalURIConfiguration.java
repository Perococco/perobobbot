package perobobbot.server.config.externaluri;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.util.Optional;

@Configuration
@ToString
@RequiredArgsConstructor
public class ExternalURIConfiguration {

    @Value("${server.port}")
    private final int serverPort;

    @Value("${webhook.external-url.mode}")
    private final @NonNull String webhookMode;

    @Value("${webhook.manual.host:}")
    private final @NonNull String host;

    @Value("${webhook.manual.context:}")
    private final @NonNull String context;

    @Value("${webhook.manual.port:-1}")
    private final int port;

    @Value("${webhook.ngrok.tunnel-name:}")
    private final @NonNull String ngrokTunnelName;

    @Value("${oauth.redirect_uri.mode}")
    private final @NonNull String oauthMode;

    @Bean(name="webhook")
    public @NonNull ExternalURI webhookExternalURI() {
        return switch (webhookMode) {
            case "manual" -> createManualExternalURI();
            case "ngrok" -> createNgrokExternalURI();
            default -> throw new IllegalStateException("Invalid type for 'webhook.external-url.mode' : '"+ webhookMode +"'");
        };
    }

    @Bean(name="oauth")
    public @NonNull ExternalURI oauthExternalURI() {
        return switch (oauthMode) {
            case "localhost" -> createLocalHostOAuthExternalURI();
            case "webhook" -> webhookExternalURI();
            default -> throw new IllegalStateException("Invalid type for 'oauth.redirect_uri.mode' : '"+ oauthMode +"'");
        };
    }

    private @NonNull ExternalURI createManualExternalURI() {
        return createHttpsExternalURI(host,context,port);
    }

    private @NonNull ExternalURI createNgrokExternalURI() {
        return new CachedExternalURI(new NgrokExternalURI(ngrokTunnelName));
    }

    private @NonNull ExternalURI createLocalHostOAuthExternalURI() {
        return createHttpExternalURI("localhost",context, serverPort);
    }


    private @NonNull ExternalURI createHttpExternalURI(@NonNull String host, @NonNull String context, int port) {
        return createExternalURI("http://"+host,context, port,80);
    }

    private @NonNull ExternalURI createHttpsExternalURI(@NonNull String host, @NonNull String context, int port) {
        return createExternalURI("https://"+host,context, port,443);
    }

    private @NonNull ExternalURI createExternalURI(@NonNull String base, @NonNull String context, int port, int defaultPort) {
        final String uri;
        if (defaultPort == port) {
            uri = base;
        } else {
            uri = base+":"+port;
        }
        return new SimpleExternalURI(URI.create(uri)).resolve(context);
    }


    private @NonNull Optional<String> prepareContext(@NonNull String context) {
        if (context.isEmpty()) {
            return Optional.empty();
        }
        if (context.startsWith("/")) {
            return Optional.of(context);
        } else {
            return Optional.of("/" + context);
        }
    }
}
