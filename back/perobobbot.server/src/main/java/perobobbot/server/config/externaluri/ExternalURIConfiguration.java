package perobobbot.server.config.externaluri;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

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

    @Value("${webhook.manual.port:-1}")
    private final int port;

    @Value("${webhook.ngrok.tunnel-name:}")
    private final @NonNull String ngrokTunnelName;

    @Value("${oauth.redirect_uri.mode}")
    private final @NonNull String oauthMode;

    @Bean(name="webhook")
    public @NonNull ExternalURI ngrokExternalURI() {
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
            case "ngrok" -> ngrokExternalURI();
            default -> throw new IllegalStateException("Invalid type for 'oauth.redirect_uri.mode' : '"+ oauthMode +"'");
        };
    }

    private @NonNull ExternalURI createManualExternalURI() {
        return createExternalURI(host,port);
    }

    private @NonNull ExternalURI createNgrokExternalURI() {
        return new CachedExternalURI(new NgrokExternalURI(ngrokTunnelName));
    }

    private @NonNull ExternalURI createLocalHostOAuthExternalURI() {
        return createExternalURI("localhost",serverPort);
    }


    private @NonNull ExternalURI createExternalURI(@NonNull String host, int port) {
        final var base = "https://"+host;
        final String uri;
        if (port == 443) {
            uri = base;
        } else {
            uri = base+":"+port;
        }
        return new SimpleExternalURI(URI.create(uri));
    }

}
