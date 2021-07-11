package perobobbot.server.config.externaluri;

import lombok.NonNull;

import java.net.URI;

public interface ExternalURI {

    @NonNull URI getURI();

    default @NonNull String getHost() {
        return getURI().getHost();
    }

}
