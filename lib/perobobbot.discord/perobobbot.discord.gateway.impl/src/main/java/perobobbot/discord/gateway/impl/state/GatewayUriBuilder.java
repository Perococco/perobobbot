package perobobbot.discord.gateway.impl.state;

import lombok.NonNull;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

public enum GatewayUriBuilder {
    INSTANCE,
    ;

    public URI createGatewayUri(@NonNull String gatewayUrl, int version, @NonNull String encoding) {

        final String correctedUrl;
        if (gatewayUrl.endsWith("/")) {
            correctedUrl = gatewayUrl;
        } else {
            correctedUrl = gatewayUrl+"/";
        }

        return UriComponentsBuilder.fromUriString(correctedUrl)
                                   .queryParam("v", version)
                                   .queryParam("encoding", encoding)
                                   .build(Map.of());
    }
}
