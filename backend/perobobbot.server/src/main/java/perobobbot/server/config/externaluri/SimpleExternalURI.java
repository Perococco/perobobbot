package perobobbot.server.config.externaluri;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.net.URI;

@RequiredArgsConstructor
public class SimpleExternalURI implements ExternalURI {

    private final @NonNull URI uri;

    @Override
    public @NonNull URI getURI() {
        return uri;
    }
}
